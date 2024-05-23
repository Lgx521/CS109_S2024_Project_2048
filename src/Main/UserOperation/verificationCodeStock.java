package Main.UserOperation;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class verificationCodeStock implements Serializable {

    @Serial
    private static final long serialVersionUID = -1979234108010952182L;

    private ArrayList<String> userDataList = new ArrayList<>();

    public String getData(int index) {
        return userDataList.get(index);
    }

    public void addData(String data) {
        userDataList.add(data);
    }

    public int getLine() {
        return userDataList.size();
    }


}
