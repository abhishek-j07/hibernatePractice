package com.orm;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "QUESTIONS")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTION_ID")
    private int questionId;

    @Column(name = "QUESTION")
    private String question;

    /*@OneToOne
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;*/

    //used to fetch answers eagerly when we will fetch questions
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Answer> answers;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    /*public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }*/

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
