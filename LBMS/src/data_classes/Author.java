package data_classes;

public class Author implements java.io.Serializable {

    String[] authorList;

    public Author(String[] authorList) {
        this.authorList = authorList;
    }

    public String[] getAuthorList() {
        return this.authorList;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        for(String author: this.authorList) {
            builder.append(author).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();

    }


}
