package gr.istl.collaborativechat.model;
public class Message{

    private  String text;
    private  String author;

    public Message(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public String getText() {
        return text;
    }
    public void setAuthor(String s){
        this.author=s;
    }
    public void setText(String s){
        this.text=s;
    }
    public String getAuthor() {
        return author;
    }
}