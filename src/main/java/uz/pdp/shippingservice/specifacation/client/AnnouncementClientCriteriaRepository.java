package uz.pdp.shippingservice.specifacation.client;


import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.shippingservice.dto.announcementClient.AnnouncementClientResponse;
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
public class AnnouncementClientCriteriaRepository {

    private final JdbcTemplate jdbcTemplate;
    private final LocalDateTimeConverter converter;
    @Value("${attach.upload.folder}")
    public String attachUploadFolder;

    public Page<AnnouncementClientResponse> findAllWithFilters(AnnouncementPageRequest page, AnnouncementClientSearchCriteria searchCriteria) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        Pageable pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);

        StringBuilder query = new StringBuilder(queryString);
        List<String> conditions = new ArrayList<>();
        ArrayList params = new ArrayList<>();

        conditions.add(" ac.active = true and ac.deleted = false ");
        if (Objects.nonNull(searchCriteria.getFromCountryId())) {
            params.add(searchCriteria.getFromCountryId());
            conditions.add(" from_country_name = ? ");
        }
        if (Objects.nonNull(searchCriteria.getToCountryId())) {
            params.add(searchCriteria.getToCountryId());
            conditions.add(" to_country_name = ? ");
        }
        if (Objects.nonNull(searchCriteria.getFromRegionId())) {
            params.add(searchCriteria.getFromRegionId());
            conditions.add(" from_region_name = ? ");
        }
        if (Objects.nonNull(searchCriteria.getToRegionId())) {
            params.add(searchCriteria.getToRegionId());
            conditions.add(" to_region_name = ? ");
        }
        if (Objects.nonNull(searchCriteria.getFromCityId())) {
            params.add(searchCriteria.getFromCityId());
            conditions.add(" from_city_name = ? ");
        }
        if (Objects.nonNull(searchCriteria.getToCityId())) {
            params.add(searchCriteria.getToCityId());
            conditions.add(" to_city_name = ? ");
        }
        if (Objects.nonNull(searchCriteria.getTimeToSendFrom()) && Objects.nonNull(searchCriteria.getTimeToSendTo())) {
            LocalDate lastDateStart = converter.convertOnlyDate(searchCriteria.getTimeToSendFrom());
            LocalDate lastDateStop = converter.convertOnlyDate(searchCriteria.getTimeToSendTo());
            params.add(lastDateStart);
            params.add(lastDateStop);
            conditions.add(" time_to_sent >= ? ");
            conditions.add(" time_to_sent <= ? ");
        } else if (Objects.nonNull(searchCriteria.getTimeToSendTo())) {
            LocalDate lastDateStop = converter.convertOnlyDate(searchCriteria.getTimeToSendTo());
            params.add(lastDateStop);
            conditions.add(" time_to_sent <= ? ");
        }else if (Objects.nonNull(searchCriteria.getTimeToSendFrom())) {
            LocalDate lastDateStart = converter.convertOnlyDate(searchCriteria.getTimeToSendFrom());
            params.add(lastDateStart);
            conditions.add(" time_to_sent >= ? ");
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
            TypeReference<ArrayList<AnnouncementClientResponse>> typeReference = new TypeReference<ArrayList<AnnouncementClientResponse>>() {
            };
            ArrayList<AnnouncementClientResponse> creditRequestLogPsList = AppUtils.convertWithJackson(maps, typeReference);
            creditRequestLogPsList.forEach(obj -> {
                List<String> list = new ArrayList<>();
                obj.getPhotos().forEach(url -> {
                    url = attachUploadFolder + url;
                    list.add(url);
                });
                obj.setPhotos(list);
            });
            return new PageImpl<AnnouncementClientResponse>(creditRequestLogPsList, pageable, totalCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Page<AnnouncementClientResponse> getClientOwnAnnouncements(AnnouncementPageRequest page, Boolean active, UserEntity userEntity) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        Pageable pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);

        StringBuilder query = new StringBuilder(queryString);
        List<String> conditions = new ArrayList<>();
        ArrayList params = new ArrayList<>();
        conditions.add(" ac.deleted = false ");
        conditions.add(" ac.created_by_id = ?");
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
            TypeReference<ArrayList<AnnouncementClientResponse>> typeReference = new TypeReference<ArrayList<AnnouncementClientResponse>>() {
            };
            ArrayList<AnnouncementClientResponse> creditRequestLogPsList = AppUtils.convertWithJackson(maps, typeReference);
            creditRequestLogPsList.forEach(obj -> {
                List<String> list = new ArrayList<>();
                obj.getPhotos().forEach(url -> {
                    url = attachUploadFolder + url;
                    list.add(url);
                });
                obj.setPhotos(list);
            });
            return new PageImpl<AnnouncementClientResponse>(creditRequestLogPsList, pageable, totalCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String queryString = "select ac.id,\n" +
            "       (select name from country where id = from_country_id) as fromcountry_name,\n" +
            "       (select name from country where id = to_country_id)   as tocountry_name,\n" +
            "       (select name from region where id = from_region_id)   as fromregion_name,\n" +
            "       (select name from region where id = to_region_id)     as toregion_name,\n" +
            "       (select name from city where id = from_city_id)       as fromcity_name,\n" +
            "       (select name from city where id = to_city_id)         as tocity_name,\n" +
            "       from_latitude, from_longitude, to_latitude, to_longitude, info, price, time_to_send,\n" +
            "       ac.created_at, u.id as user_id, u.phone as phone, u.surname || ' ' || u.name as fullname,\n" +
            "      (select array_agg(aa.path || '/' ||aa.new_name||'.'||aa.content_type) as photos from\n" +
            "         announcement_client_photos an ,attachment aa\n" +
            "        where an.announcement_client_id =ac.id and an.photos_id = aa.id) as photos\n" +
            "from announcement_client ac ,users u  where u.id = ac.created_by_id ";


}


