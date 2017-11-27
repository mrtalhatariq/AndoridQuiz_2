package inhouseproduct.androidquiz2.view.signup;

import java.util.List;

public interface ItemsInteractor {

    interface OnFinishedListener {
        void onFinished(List<String> items);
    }

    void setItems(OnFinishedListener listener);
}