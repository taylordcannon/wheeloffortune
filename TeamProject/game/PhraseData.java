package game;

import java.io.Serializable;
import java.util.ArrayList;

public class PhraseData implements Serializable{
//houses question, answer, and users choice
	
	//string for question, string for correct answer, string for useranswer
	
	  private String Question;
	  private ArrayList<String> Answers;
	  private String UserAnswer;
	  private String RightAnswer;
	  
	  public PhraseData() {
		  setUserAnswer("");
		 }
	  
	  // Getters for the username and password.
	  public String getQuestion()
	  {
	    return Question;
	  }
	  public ArrayList<String> getAnswer()
	  {
	    return Answers;
	  }
	  public String getUserAnswer()
	  {
	    return UserAnswer;
	  }
	  public String getRightAnswer()
	  {
		  return RightAnswer;
	  }
	  
	  
	  // Setters for the username and password.
	  public void setQuestion(String Question)
	  {
	    this.Question = Question;
	  }
	  public void setAnswers(ArrayList<String> Answer)
	  {
	    this.Answers = Answer;
	  }
	  public void setUserAnswer(String UserAnswer)
	  {
	    this.UserAnswer = UserAnswer;
	  }
	  public void setRightAnswer(String RightAnswer)
	  {
	    this.RightAnswer = RightAnswer;
	  }
	  
	  // Constructor that initializes the username and password.
	  public PhraseData(String Question, ArrayList<String> Answer, String UserAnswer, String RightAnswer)
	  {
	    setQuestion(Question);
	    setUserAnswer(UserAnswer);
	    setAnswers(Answer);
	    setRightAnswer(RightAnswer);
	  }
	}

