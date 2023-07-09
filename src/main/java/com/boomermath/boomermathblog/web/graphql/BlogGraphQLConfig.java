package com.boomermath.boomermathblog.web.graphql;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.integration.graphql.GraphQLEntityViewSupport;
import com.blazebit.persistence.integration.graphql.GraphQLEntityViewSupportFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.boomermath.boomermathblog.data.repositories.BlogArticleViewRepository;
import graphql.schema.idl.SchemaGenerator;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BlogGraphQLConfig {

    private GraphQLEntityViewSupport graphQLEntityViewSupport;

    /*
    blogArticleViewRepository.findById(
                                                graphQLEntityViewSupport.createSetting(env),
                                                UUID.fromString(env.getArgument("slug")))
     */

    @Bean
    public <T> GraphQlSourceBuilderCustomizer graphQlConfig(EntityManager em, EntityViewManager evm, BlogArticleViewRepository blogArticleViewRepository, CriteriaBuilderFactory cbf) {
        GraphQLEntityViewSupportFactory graphQLEntityViewSupportFactory =
                new GraphQLEntityViewSupportFactory(true, true);
        graphQLEntityViewSupportFactory.setImplementRelayNode(false);
        graphQLEntityViewSupportFactory.setDefineRelayNodeIfNotExist(true);

        return builder -> {
            builder
                    .schemaFactory(((typeDefinitionRegistry, runtimeWiring) -> {
                        graphQLEntityViewSupport = graphQLEntityViewSupportFactory.create(typeDefinitionRegistry, evm);
                        return new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
                    }))
                    .configureRuntimeWiring(configurer -> {
                        configurer.type("Query", typeConfigBuilder -> typeConfigBuilder.dataFetcher("getArticle", env -> {
                            EntityViewSetting<T, CriteriaBuilder<T>> entityViewSetting = graphQLEntityViewSupport.createSetting(env);

                            for (String fetch : entityViewSetting.getFetches()) {
                                log.info(fetch);
                            }

                            return blogArticleViewRepository.findById(
                                    entityViewSetting, UUID.fromString(env.getArgument("slug"))
                            );
                        }));
                    });
        };
    }
}
