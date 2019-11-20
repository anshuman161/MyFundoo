package com.bridgelabz.fundooproject.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundooproject.model.NoteDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

	private static final String INDEX = "dbnote";

	private static final String TYPE = "notes";

	@Autowired  
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public String createNote(NoteDetails userNote) {
		@SuppressWarnings("unchecked")
		Map<String, Object> Mapper = objectMapper.convertValue(userNote, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, String.valueOf(userNote.getNoteId())).source(Mapper);

		IndexResponse indexResponse = null;
		try {
			indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return indexResponse.getResult().name();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updateNote(NoteDetails note) {
		UpdateResponse updateResponse = null;

		@SuppressWarnings("unchecked")
		Map<String, String> mapper = objectMapper.convertValue(note, Map.class);

		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(note.getNoteId())).doc(mapper);
		try {
			updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return updateResponse.getResult().name();
	}

	@Override
	public String deleteNote(NoteDetails note) {
		DeleteResponse deleteResponse = null;

		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, String.valueOf(note.getNoteId()));
		try {
			deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return deleteResponse.getResult().name();
	}

	@Override
	public List<NoteDetails> searchNoteByData(String searchData) {
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(searchData).lenient(true).field("tittle")
						.field("description"))
				.should(QueryBuilders.queryStringQuery("*" + searchData + "*").lenient(true).field("tittle")
						.field("description"));
		searchRequest.source(searchSourceBuilder.query(queryBuilder));
		SearchResponse response = new SearchResponse();
		try {
			response = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getSearchResult(response);
	}

	private List<NoteDetails> getSearchResult(SearchResponse response) {
		SearchHit[] searchHit = response.getHits().getHits();
		List<NoteDetails> profileDocuments = new ArrayList<>();
		if (searchHit.length > 0) {
			Arrays.stream(searchHit).forEach(
					hit -> profileDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), NoteDetails.class)));
		}
		return profileDocuments;
	}
	
	
	
	
	
	@Override
	public List<NoteDetails> search(String tittle, String description) {
	SearchRequest searchRequest = new SearchRequest();
	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	QueryBuilder queryBuilder = QueryBuilders.boolQuery()
	.should(QueryBuilders.queryStringQuery(tittle).lenient(true).field("tittle")
	.field("description"))
	.should(QueryBuilders.queryStringQuery("*" + tittle + "*").lenient(true).field("tittle")
	.field("description"));
	searchRequest.source(searchSourceBuilder.query(queryBuilder));
	SearchResponse response = new SearchResponse();
	try {
	response = client.search(searchRequest, RequestOptions.DEFAULT);
	} catch (IOException e) {
	e.printStackTrace();
	}
	return getSearchResult(response);
	}
}
