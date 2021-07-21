package com.wootech.dropthecode.controller.util;

import java.io.IOException;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.ContentModifier;

public class PrettyPrintingUtils implements ContentModifier {

    public PrettyPrintingUtils() {}

    @Override
    public byte[] modifyContent(byte[] originalContent, MediaType contentType) {
        if (originalContent.length > 0) {
            try {
                return new JsonPrettyPrinter().prettyPrint(originalContent);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        return originalContent;
    }

    private static final class JsonPrettyPrinter {
        private final ObjectMapper objectMapper;

        private JsonPrettyPrinter() {
            this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        }

        public byte[] prettyPrint(byte[] original) throws IOException {
            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
            return this.objectMapper.setDefaultPrettyPrinter(prettyPrinter)
                                    .writeValueAsBytes(this.objectMapper.readTree(original));
        }
    }
}
