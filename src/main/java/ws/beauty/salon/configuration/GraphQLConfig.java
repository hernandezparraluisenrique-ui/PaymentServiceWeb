package ws.beauty.salon.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.Duration;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Configuration;
import graphql.schema.CoercingSerializeException;
import org.springframework.stereotype.Component;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(iso8601DurationScalar());
    }

    @Bean
    public GraphQLScalarType iso8601DurationScalar() {
        return GraphQLScalarType.newScalar()
                .name("ISO8601Duration")
                .description("ISO-8601 Duration format (e.g., PT1H30M for 1 hour 30 minutes)")
                .coercing(new Coercing<Duration, String>() {
                    @Override
                    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                        if (dataFetcherResult == null) {
                            return null;
                        }
                        if (dataFetcherResult instanceof Duration) {
                            return dataFetcherResult.toString();
                        }
                        throw new CoercingSerializeException(
                            "Expected Duration but got: " + dataFetcherResult.getClass().getName()
                        );
                    }

                    @Override
                    public Duration parseValue(Object input) throws CoercingParseValueException {
                        if (input == null) {
                            return null;
                        }
                        try {
                            return Duration.parse(input.toString());
                        } catch (Exception e) {
                            throw new CoercingParseValueException(
                                "Invalid ISO-8601 Duration format: " + input + 
                                ". Expected format like 'PT1H30M' (1 hour 30 minutes)", e
                            );
                        }
                    }

                    @Override
                    public Duration parseLiteral(Object input) throws CoercingParseLiteralException {
                        if (input == null) {
                            return null;
                        }
                        if (input instanceof StringValue) {
                            try {
                                String value = ((StringValue) input).getValue();
                                return Duration.parse(value);
                            } catch (Exception e) {
                                throw new CoercingParseLiteralException(
                                    "Invalid ISO-8601 Duration format. Expected format like 'PT1H30M'", e
                                );
                            }
                        }
                        throw new CoercingParseLiteralException(
                            "Expected StringValue but got: " + input.getClass().getName()
                        );
                    }
                })
                .build();
    }
}
