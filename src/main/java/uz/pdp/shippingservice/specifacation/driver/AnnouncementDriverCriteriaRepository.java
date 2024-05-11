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

        conditions.add(" ac.is_active = true and ac.is_deleted = false ");
        if (Objects.nonNull(searchCriteria.getCountryId())) {
            params.add(searchCriteria.getCountryId());
            conditions.add(" country_id = ? ");
        }
        if (Objects.nonNull(searchCriteria.getRegionId())) {
            params.add(searchCriteria.getRegionId());
            conditions.add(" region_id = ? ");
        }
        if (Objects.nonNull(searchCriteria.getCityId())) {
            params.add(searchCriteria.getCityId());
            conditions.add(" city_id = ? ");
        }
        if (Objects.nonNull(searchCriteria.isCanGoAnotherCountry())) {
            params.add(searchCriteria.isCanGoAnotherCountry());
            conditions.add(" can_go_another_country = ? ");
        }
        if (Objects.nonNull(searchCriteria.isCanGoAnotherRegion())) {
            params.add(searchCriteria.isCanGoAnotherRegion());
            conditions.add(" can_go_another_region = ? ");
        }
        if (Objects.nonNull(searchCriteria.isOnlyCity())) {
            params.add(searchCriteria.isOnlyCity());
            conditions.add(" only_city = ? ");
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
            conditions.add(" time_to_drive = ? ");
        }else if (Objects.nonNull(searchCriteria.getTimeToDriveFrom())) {
            LocalDate lastDateStop = converter.convertOnlyDate(searchCriteria.getTimeToDriveFrom());
            params.add(lastDateStop);
            conditions.add(" time_to_drive = ? ");
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
        conditions.add(" ac.deleted = false ");
        conditions.add(" ac.createdby_id = ?");
        params.add(userEntity.getId());
        if (Objects.nonNull(active)) {
            params.add(active);
            conditions.add(" ac.active = ? ");
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

    private String queryString = "select ac.id,\n" +
            "       (select name from country where id = fromcountry_id) as fromcountry_name,\n" +
            "       (select name from country where id = tocountry_id)   as tocountry_name,\n" +
            "       (select name from region where id = fromregion_id)   as fromregion_name,\n" +
            "       (select name from region where id = toregion_id)     as toregion_name,\n" +
            "       (select name from city where id = fromcity_id)       as fromcity_name,\n" +
            "       (select name from city where id = tocity_id)         as tocity_name,\n" +
            "       from_latitude, from_longitude, to_latitude, to_longitude, info, price, time_to_send,\n" +
            "       ac.created_at, u.id as user_id, u.phone as phone, u.surname || ' ' || u.name as fullname,\n" +
            "      (select array_agg(aa.path || '/' ||aa.new_name||'.'||aa.content_type) as photos from\n" +
            "         announcement_client_attachment an ,attachment aa\n" +
            "        where an.announcementclient_id =ac.id and an.photos_id = aa.id) as photos\n" +
            "from announcement_client ac ,users u  where u.id = ac.createdby_id ";


}


