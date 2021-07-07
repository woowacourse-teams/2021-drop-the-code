package com.wootech.dropthecode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

    public static MockMvc restDocsMockMvc(RestDocumentationContextProvider provider, Object... controllers) {
        return MockMvcBuilders.standaloneSetup(controllers)
                              .alwaysDo(prepareJackson(OBJECT_MAPPER))
                              .alwaysDo(restDocumentation())

                              /*
                                   RestDocumentationContextProvider 은 RestDocumentationContext 에 접근하기 위한 인터페이스.
                                   RestDocumentationContext 은 문서화할 API 문서를 캡슐화한 클래스.

                                   documentationConfiguration 은RestDocumentationContextProvider 을 인수로 받는다.
                                   RestDocumentationExtension 클래스가 @ExtendWith 으로 선언되어 있을 경우 @BeforeEach 로 각 테스트가 시작 될 때마다,
                                   ManualRestDocumentation 의 context 를 초기화한다.

                                   만약 @BeforeEach 를 사용하지 않거나 해서 어쨌든 ManualRestDocumentation#beforeTest() 를 실행하지 않을 경우
                                   ManualRestDocumentation 의 context 가 null 에서 초기화 되지 않아 NPE 발생

                                   현재 이 메소드에서 new ManualRestDocumentation() 을 하지 않은 이유가 위의 이유 때문이다.
                                   ManualRestDocumentation#beforeTest() 메소드를 실행하려면, 실행 테스트의 클래스와 이름을 알아야 하는데,
                                   이것을 여기서는 알지 못한다.

                                   또한 매 테스트마다 provider 가 초기화되어야 하기 때문에 상태가 계속 변한다.
                                   그래서 이 클래스를 빈으로 등록하지 않았다.
                               */
                              .apply(documentationConfiguration(provider)
                                      .uris()
                                      .withScheme("http")
                                      .withHost("localhost")
                                      .withPort(8080)
                                      .and()
                                      .snippets()
                                      .withDefaults(
                                              HttpDocumentation.httpRequest(),
                                              HttpDocumentation.httpResponse(),
                                              AutoDocumentation.requestFields(),
                                              AutoDocumentation.responseFields(),
                                              AutoDocumentation.pathParameters(),
                                              AutoDocumentation.requestParameters(),
                                              AutoDocumentation.description(),
                                              AutoDocumentation.methodAndPath(),
                                              AutoDocumentation.sectionBuilder()
                                                               .snippetNames(
                                                                       AUTO_METHOD_PATH,
                                                                       AUTO_DESCRIPTION,
                                                                       AUTO_AUTHORIZATION,
                                                                       AUTO_PATH_PARAMETERS,
                                                                       AUTO_REQUEST_PARAMETERS,
                                                                       AUTO_REQUEST_FIELDS,
                                                                       AUTO_RESPONSE_FIELDS,
                                                                       HTTP_REQUEST,
                                                                       HTTP_RESPONSE
                                                               )
                                                               .skipEmpty(true)
                                                               .build(),
                                              AutoDocumentation.authorization(PUBLIC_AUTHORIZATION)
                                      ))
                              .build();
    }

    private static RestDocumentationResultHandler restDocumentation(Snippet... snippets) {
        return MockMvcRestDocumentation
                .document("{class-name}/{method-name}",
                        preprocessRequest(

                                // RestDocs 스니펫 이름 설정 및 Request 와 Response 를 정리하여 출력
                                prettyPrint(),

                                // 지정 헤더를 제외한 스니펫을 생성
                                removeHeaders("Host", "Content-Length")
                        ),
                        preprocessResponse(
                                replaceBinaryContent(),
                                limitJsonArrayLength(OBJECT_MAPPER),
                                prettyPrint(),
                                removeHeaders("Content-Length")
                        ),
                        snippets
                );

    }
}
