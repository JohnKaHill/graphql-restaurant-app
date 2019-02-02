package de.pwc.digispace.javadevcourse.backend.server;

import java.util.LinkedHashMap;
import java.util.Map;

import com.coxautodev.graphql.tools.SchemaParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pwc.digispace.javadevcourse.backend.BeverageRepository;
import de.pwc.digispace.javadevcourse.resolver.Mutation;
import de.pwc.digispace.javadevcourse.resolver.Query;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

public class GraphQLEndpoint{

	private GraphQLSchema graphQLSchema;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(GraphQLEndpoint.class);
	
	private static final BeverageRepository BEVERAGE_REPOSITORY = new BeverageRepository();


	public GraphQLEndpoint() {
		this.graphQLSchema = buildSchema();
	}
	
	private static GraphQLSchema buildSchema() {		
		return SchemaParser.newParser()
				.file("schema.graphqls")
				.resolvers(
						new Mutation( BEVERAGE_REPOSITORY ),
						new Query( BEVERAGE_REPOSITORY ) )
				.build()
				.makeExecutableSchema();
	}
	
	public String executeQuery( String requestBody ) {

		GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

		ExecutionInput executionInput = ExecutionInput
				.newExecutionInput()
				.query( getRequestFormat(requestBody) )
				.build();

		ExecutionResult executionResult = graphQL
				.execute(executionInput);

		return new Gson()
				.toJson(executionResult.getData(), LinkedHashMap.class);

	}

	// formats the http Request so that the ExecutionInput can handel it!
	private String getRequestFormat( String request ) {
		Gson GSON = new Gson();
		TypeToken<Map<String, Object>> typeToken = new TypeToken<Map<String, Object>>(){};
		try {
			Map<String, Object> map = GSON.fromJson(request, typeToken.getType());
			return (String) map.get("query");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
