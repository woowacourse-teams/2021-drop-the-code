package com.wootech.dropthecode.controller.util;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wootech.dropthecode.exception.GlobalExceptionHandler;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.ContentModifyingOperationPreprocessor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;

import capital.scalable.restdocs.AutoDocumentation;

import static capital.scalable.restdocs.SnippetRegistry.*;
import static capital.scalable.restdocs.jackson.JacksonResultHandlers.prepareJackson;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public class RestDocsMockMvcUtils {

    private static final String PUBLIC_AUTHORIZATION = "Resource is public.";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Snippet[] SUCCESS_SNIPPETS = new Snippet[]{
            HttpDocumentation.httpRequest(),
            HttpDocumentation.httpResponse(),
            AutoDocumentation.requestFields(),
            AutoDocumentation.responseFields(),
            AutoDocumentation.pathParameters(),
            AutoDocumentation.requestParameters(),
            AutoDocumentation.description(),
            AutoDocumentation.methodAndPath(),
            AutoDocumentation.modelAttribute(Arrays.asList(
                    MockMvcConfig.pageableHandlerMethodArgumentResolver(),
                    MockMvcConfig.modelAttributeMethodProcessor())),
            AutoDocumentation.sectionBuilder().snippetNames(
                    AUTO_METHOD_PATH,
                    AUTO_DESCRIPTION,
                    AUTO_AUTHORIZATION,
                    AUTO_PATH_PARAMETERS,
                    AUTO_MODELATTRIBUTE,
                    AUTO_REQUEST_PARAMETERS,
                    AUTO_REQUEST_FIELDS,
                    AUTO_RESPONSE_FIELDS,
                    HTTP_REQUEST,
                    HTTP_RESPONSE
            ).skipEmpty(true).build(),
            AutoDocumentation.authorization(PUBLIC_AUTHORIZATION)};

    private static final Snippet[] FAIL_SNIPPETS = new Snippet[]{HttpDocumentation.httpResponse()};

    @TestConfiguration
    public static class MockMvcConfig {

        @Bean
        public static GlobalExceptionHandler controllerAdvice() {
            return new GlobalExceptionHandler();
        }

        @Bean
        public static CharacterEncodingFilter utf8Filter() {
            return new CharacterEncodingFilter("UTF-8", true);
        }

        @Bean
        public static PrettyPrintingUtils prettyPrintingUtils() {
            return new PrettyPrintingUtils();
        }

        @Bean
        public static ContentModifyingOperationPreprocessor prettyPrintPreProcessor() {
            return new ContentModifyingOperationPreprocessor(prettyPrintingUtils());
        }

        @Bean
        public static ModelAttributeMethodProcessor modelAttributeMethodProcessor() {
            return new ModelAttributeMethodProcessor(false);
        }

        @Bean
        public static PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
            return new PageableHandlerMethodArgumentResolver();
        }
    }

    public static MockMvc successRestDocsMockMvc(RestDocumentationContextProvider provider, Object... controllers) {
        return restDocsMockMvc(provider, SUCCESS_SNIPPETS, controllers);
    }

    public static MockMvc failRestDocsMockMvc(RestDocumentationContextProvider provider, Object... controllers) {
        return restDocsMockMvc(provider, FAIL_SNIPPETS, controllers);
    }

    public static MockMvc restDocsMockMvc(RestDocumentationContextProvider provider, Snippet[] snippets, Object... controllers) {
        return MockMvcBuilders.standaloneSetup(controllers)
                              .addFilters(MockMvcConfig.utf8Filter())
                              .setControllerAdvice(MockMvcConfig.controllerAdvice())
                              .setCustomArgumentResolvers(MockMvcConfig.pageableHandlerMethodArgumentResolver())
                              .alwaysDo(prepareJackson(OBJECT_MAPPER))
                              .alwaysDo(restDocumentation())
                              .apply(documentationConfiguration(provider)
                                      .uris()
                                      .withScheme("http")
                                      .withHost("localhost")
                                      .withPort(8080)
                                      .and()
                                      .snippets()
                                      .withDefaults(snippets))
                              .build();
    }

    public static RestDocumentationResultHandler restDocumentation(Snippet... snippets) {
        return MockMvcRestDocumentation
                .document("{class-name}/{method-name}",
                        preprocessRequest(

                                // RestDocs 스니펫 이름 설정 및 Request 와 Response 를 정리하여 출력
                                MockMvcConfig.prettyPrintPreProcessor(),

                                // 지정 헤더를 제외한 스니펫을 생성
                                removeHeaders("Host", "Content-Length")
                        ),
                        preprocessResponse(
                                replaceBinaryContent(),
                                limitJsonArrayLength(OBJECT_MAPPER),
                                MockMvcConfig.prettyPrintPreProcessor(),
                                removeHeaders("Content-Length")
                        ),
                        snippets
                );

    }
}
