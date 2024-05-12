package uz.pdp.shippingservice.specifacation.driver;


import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverResponse;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.service.LocalDateTimeConverter;
import uz.pdp.shippingservice.specifacation.AnnouncementPageRequest;
import uz.pdp.shippingservice.utils.AppUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
@RequiredArgsConstructor
public class AnnouncementDriverCriteriaRepository {

    private final JdbcTemplate jdbcTemplate;
    private final LocalDateTimeConverter converter;
    @Value("${attach.upload.folder}")
    public String attachUploadFolder;

    public Page<AnnouncementDriverResponse> findAllWithFilters(AnnouncementPageRequest page, AnnouncementDriverSearchCriteria searchCriteria) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        Pageable pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);

        StringBuilder query = new StringBuilder(queryString);
        List<String> conditions = new ArrayList<>();
        ArrayList params = new ArrayList<>();

        conditions.add(" ac.active = true and ac.deleted = false ");
        if (Objects.nonNull(searchCriteria.getCountryId())) {
            params.add(searchCriteria.getCountryId());
            conditions.add(" ad.country_id = ? ");
        }
        if (Objects.nonNull(searchCriteria.getRegionId())) {
            params.add(searchCriteria.getRegionId());
            conditions.add(" ad.region_id = ? ");
        }
        if (Objects.nonNull(searchCriteria.getCityId())) {
            params.add(searchCriteria.getCityId());
            conditions.add(" ad.city_id = ? ");
        }
        if (Objects.nonNull(searchCriteria.isCanGoAnotherCountry())) {
            params.add(searchCriteria.isCanGoAnotherCountry());
            conditions.add(" ad.can_go_another_country = ? ");
        }
        if (Objects.nonNull(searchCriteria.isCanGoAnotherRegion())) {
            params.add(searchCriteria.isCanGoAnotherRegion());
            conditions.add(" ad.can_go_another_region = ? ");
        }
        if (Objects.nonNull(searchCriteria.isOnlyCity())) {
            params.add(searchCriteria.isOnlyCity());
            conditions.add(" ad.only_city = ? ");
        }
        if (Objects.nonNull(searchCriteria.getTimeToDriveFrom()) && Objects.nonNull(searchCriteria.getTimeToDriveTo())) {
            LocalDate lastDateStart = converter.convertOnlyDate(searchCriteria.getTimeToDriveFrom());
            LocalDate lastDateStop = converter.convertOnlyDate(searchCriteria.getTimeToDriveTo());
            params.add(lastDateStart);
            params.add(lastDateStop);
            conditions.add(" time_to_drive >= ? ");
            conditions.add(" time_to_drive <= ? ");
        } else if (Objects.nonNull(searchCriteria.getTimeToDriveTo()) ) {
            LocalDate lastDateStop = converter.convertOnlyDate(searchCriteria.getTimeToDriveTo());
            params.add(lastDateStop);
            conditions.add(" time_to_drive <= ? ");
        }else if (Objects.nonNull(searchCriteria.getTimeToDriveFrom())) {
            LocalDate lastDateStart = converter.convertOnlyDate(searchCriteria.getTimeToDriveFrom());
            params.add(lastDateStart);
            conditions.add(" time_to_drive >= ? ");
        }

        if (!conditions.isEmpty()) {
            query.append(String.join(" and ", conditions));
//            query.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        String totalCountQuery = query.toString();
        query.append(" order by ").append(page.getSortBy()).append(" ").append(page.getSortDirection());
        query.append(" limit ? offset ?");
        params.add(page.getPageSize());
        params.add(page.getPageSize() * page.getPageNumber());
        try {
            Long totalCount = jdbcTemplate.queryForObject(totalCountQuery, Long.class);
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(query.toString(), params.toArray());
            TypeReference<ArrayList<AnnouncementDriverResponse>> typeReference = new TypeReference<ArrayList<AnnouncementDriverResponse>>() {
            };
            ArrayList<AnnouncementDriverResponse> creditRequestLogPsList = AppUtils.convertWithJackson(maps, typeReference);

            return new PageImpl<AnnouncementDriverResponse>(creditRequestLogPsList, pageable, totalCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Page<AnnouncementDriverResponse> getDrierOwnAnnouncements(AnnouncementPageRequest page, Boolean active, UserEntity userEntity) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        Pageable pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);

        StringBuilder query = new StringBuilder(queryString);
        List<String> conditions = new ArrayList<>();
        ArrayList params = new ArrayList<>();
        conditions.add(" ad.deleted = false ");
        conditions.add(" ad.user_entity_id = ?");
        params.add(userEntity.getId());
        if (Objects.nonNull(active)) {
            params.add(active);
            conditions.add(" ad.active = ? ");
        }
        if (!conditions.isEmpty()) {
            query.append(String.join(" and ", conditions));
//            query.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        String totalCountQuery = query.toString();
        query.append(" order by ").append(page.getSortBy()).append(" ").append(page.getSortDirection());
        query.append(" limit ? offset ?");
        params.add(page.getPageSize());
        params.add(page.getPageSize() * page.getPageNumber());
        try {
            Long totalCount = jdbcTemplate.queryForObject(totalCountQuery, Long.class);
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(query.toString(), params.toArray());
            TypeReference<ArrayList<AnnouncementDriverResponse>> typeReference = new TypeReference<ArrayList<AnnouncementDriverResponse>>() {
            };
            ArrayList<AnnouncementDriverResponse> creditRequestLogPsList = AppUtils.convertWithJackson(maps, typeReference);

            return new PageImpl<AnnouncementDriverResponse>(creditRequestLogPsList, pageable, totalCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String queryString = "select ad.id,\n" +
            " ad.current_latitude, ad.current_longitude,ad.info,ad.time_to_drive ,ad.created_at,\n" +
            " ad.can_go_another_country, ad.can_go_another_region, ad.only_city,\n" +
            " (select name from country where id = country_id) as country_name,\n" +
            " (select name from region where id = region_id)   as region_name,\n" +
            " (select name from city where id = city_id)       as city_name,\n" +
            " u.id as user_id, u.phone as phone, u.surname || ' ' || u.name as fullname,\n" +
            " di.id as driver_info_id, di.model , di.car_length ,di.car_width ,di.max_load,\n" +
            " di.has_freezer, di.has_wrapped_fully \n" +
            " from announcement_driver ad ,users u ,driver_info di where u.id = ad.user_entity_id and di.user_id = u.id and di.is_active = true ";


}


