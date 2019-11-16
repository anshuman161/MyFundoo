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

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class ElasticServiceImpl implements ElasticService{

@Autowired
private RestHighLevelClient client;

@Autowired
private ObjectMapper mapper;

private static final String index = "notes";

private static final String type = "notes_data";


@Override
public void save(NoteDetails note) {
try {
System.out.println("entered into elastc service");
Map<String, String> noteMap = mapper.convertValue(note, Map.class);
IndexRequest request = new IndexRequest(index, type).id("" + note.getNoteId()).source(noteMap);
IndexResponse response = client.index(request, RequestOptions.DEFAULT);
log.info(response.status().toString());
} catch (IOException e) {
e.printStackTrace();
}

}

@Override
public void update(long noteId, NoteDetails modifynote) {

Map<String, String> noteMap = mapper.convertValue(modifynote, Map.class);
UpdateRequest request = new UpdateRequest(index, type, modifynote.getNoteId()+"");
request.doc(noteMap);
try {
UpdateResponse response=client.update(request, RequestOptions.DEFAULT);
log.info(response.status().toString());
} catch (IOException e) {
e.printStackTrace();
}
}

@Override
public void deleteNote(NoteDetails modifynote) {
DeleteRequest request=new DeleteRequest(index, type, modifynote.getNoteId()+"");
try {
DeleteResponse response=client.delete(request, RequestOptions.DEFAULT);
log.info(response.toString());
} catch (IOException e) {
e.printStackTrace();
}

}

@Override
public List<NoteDetails> search(String tittle, String description) {
SearchRequest searchRequest = new SearchRequest();
SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
QueryBuilder queryBuilder = QueryBuilders.boolQuery()
.should(QueryBuilders.queryStringQuery(tittle).lenient(true).field("title")
.field("description"))
.should(QueryBuilders.queryStringQuery("*" + tittle + "*").lenient(true).field("title")
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
hit -> profileDocuments.add(mapper.convertValue(hit.getSourceAsMap(), NoteDetails.class)));
}
System.err.println(profileDocuments);


return profileDocuments;
}


}
