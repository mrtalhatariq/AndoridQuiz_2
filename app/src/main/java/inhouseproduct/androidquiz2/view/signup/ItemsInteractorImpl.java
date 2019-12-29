package inhouseproduct.androidquiz2.view.signup;

import java.util.Arrays;
import java.util.List;

import inhouseproduct.androidquiz2.DB.DbOps;
import inhouseproduct.androidquiz2.DB.models.UserType;

public class ItemsInteractorImpl implements ItemsInteractor {


    @Override public void setItems(final OnFinishedListener listener) {
        listener.onFinished(createArrayList());

        for (int i = 0; i < createArrayList().size(); i++) {

//            DbOps.insert(new UserType(createArrayList().get(i)));


        }

    }

    private List<String> createArrayList() {
        return Arrays.asList(
                "Select user type",
                "Broker",
                "Agent",
                "Dealer",
                "Private"
        );
    }
}