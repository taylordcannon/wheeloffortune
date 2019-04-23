package game;

import java.io.Serializable;
import java.util.ArrayList;

public class PhraseData implements Serializable{
	private String question;
	private ArrayList<String> answers;
	private ArrayList<String> questions;
	private String userAnswer;
	private ArrayList<String> rightAnswer;
	
	public PhraseData() {
		setUserAnswer("");
	}
	
	public ArrayList<String> getAnswer(){
		return answers;
	}
	
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}
	
	public ArrayList<String> getQuestions(){
		return questions;
	}
	
	public void setQuestions(ArrayList<String> questions) {
		this.questions = questions;
	}
	
	public String getUserAnswer() {
		return userAnswer;
	} 
	
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	
	public ArrayList<String> getRightAnswers(){
		return rightAnswer;
	}
	
	public void setRightAnswers(ArrayList<String> rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	
	public PhraseData(ArrayList<String> question, ArrayList<String> answer, String userName, ArrayList<String> rightAnswer){
		setQuestions(question);
		setUserAnswer(userAnswer);
		setAnswers(answer);
		setRightAnswers(rightAnswer);
	}
}
