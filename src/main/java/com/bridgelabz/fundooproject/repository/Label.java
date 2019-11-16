package com.bridgelabz.fundooproject.repository;

import java.util.List;

import com.bridgelabz.fundooproject.model.LabelDetails;
import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.UserInformation;

public interface Label {
public void save(LabelDetails label);
public UserInformation findById(long userId);
public NoteDetails findNoteById(long id) ;
public LabelDetails findLabelById(long labelId);
public LabelDetails fetchLabelByName(String labelName);
public int deleteLabel(long labelId);
public LabelDetails editLabel(LabelDetails labelDetails);
public List<LabelDetails> fetchAllLabelById(long id);
public int updateLabelById(long labelId,LabelDetails label);

}
