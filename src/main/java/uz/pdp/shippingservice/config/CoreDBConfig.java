//package uz.pdp.shippingservice.config;
//
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = {
//                "uz.pdp.shippingservice.entity",
//                "uz.pdp.shippingservice.repository"
//        }
//)
//public class CoreDBConfig {
//    @Primary
//    @Bean(name = "dataSource")
//    @ConfigurationProperties("spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Primary
//    @Bean(name = "jdbcCoreDb")
//    public JdbcTemplate mainJdbcTemplate(@Qualifier("dataSource") DataSource dsMaster) {
//        return new JdbcTemplate(dsMaster);
//    }
//
//    @Primary
//    @Bean(name = "entityManagerFactoryBuilder")
//    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
//        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap(), null);
//    }
//
//    @Primary
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            @Qualifier("entityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
//            @Qualifier("dataSource") DataSource dataSource
//    ) {
//
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", "create");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//
//        return builder
//                .dataSource(dataSource)
//                .packages("uz.pdp.shippingservice.entity")
//                .properties(properties)
//                .build();
//
//    }
//
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory absEntityManagerFactory) {
//        return new JpaTransactionManager(absEntityManagerFactory);
//    }
//}
