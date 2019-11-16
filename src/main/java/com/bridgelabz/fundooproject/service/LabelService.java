package com.bridgelabz.fundooproject.service;

import java.util.List;

import com.bridgelabz.fundooproject.model.LabelDetails;

public interface LabelService {
	public void save(LabelDetails label, String token);

	public boolean delete(long labelId, String token);

	public boolean addNoteToLabel(long labelId, long noteId, String token);

	public void edit(long labelId, LabelDetails label, String token);
	
	public List<LabelDetails> fetchAllLabel(String token);

	public void updatelabel(long labelId,LabelDetails label, String token);

	public boolean deleteNoteToLabel(long labelId, long noteId, String token);


}
