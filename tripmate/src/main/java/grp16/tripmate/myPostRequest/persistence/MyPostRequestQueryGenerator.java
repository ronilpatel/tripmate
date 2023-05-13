package grp16.tripmate.myPostRequest.persistence;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.post.persistance.PostDbColumnNames;
import grp16.tripmate.myPostRequest.model.PostRequestStatus;
import grp16.tripmate.user.persistence.UserDbColumnNames;

public class MyPostRequestQueryGenerator implements IMyPostRequestQueryGenerator {

    private final ILogger logger = new MyLoggerAdapter(this);

    private static IMyPostRequestQueryGenerator instance;

    private MyPostRequestQueryGenerator() {

    }

    public static IMyPostRequestQueryGenerator getInstance() {
        if (instance == null) {
            instance = new MyPostRequestQueryGenerator();
        }
        return instance;
    }

    @Override
    public String getMyPostRequests(int loginUserId) {
        String query = "SELECT pr." + MyPostRequestDBColumnNames.ID + " as requestId, u." + UserDbColumnNames.FIRSTNAME + " as firstNameRequester, u." + UserDbColumnNames.LASTNAME + " as lastNameRequester, u." + UserDbColumnNames.ID + " as idRequester, p." + PostDbColumnNames.TITLE + " as postTitle, " + "p." + PostDbColumnNames.OWNER + " as idCreator, post_owner." + UserDbColumnNames.FIRSTNAME + " as firstNameCreator, post_owner." + UserDbColumnNames.LASTNAME + " lastNameCreator \n" + "FROM " + MyPostRequestDBColumnNames.TABLE_NAME + " pr\n" + "JOIN " + PostDbColumnNames.TABLE_NAME + " p on pr." + MyPostRequestDBColumnNames.POST_ID + " = p." + PostDbColumnNames.ID + "\n" + "JOIN " + UserDbColumnNames.TABLE_NAME + " u on pr." + MyPostRequestDBColumnNames.REQUEST_OWNER + " = u." + UserDbColumnNames.ID + "\n" + "JOIN " + UserDbColumnNames.TABLE_NAME + " post_owner on post_owner." + UserDbColumnNames.ID + " = p." + PostDbColumnNames.OWNER + "\n" + "WHERE pr." + MyPostRequestDBColumnNames.STATUS + " = \"PENDING\" and p." + PostDbColumnNames.OWNER + " = " + loginUserId + ";";

        logger.info(query);
        return query;
    }


    @Override
    public String createJoinRequest(int post_id, int user_id) {
        String query = "INSERT INTO " + MyPostRequestDBColumnNames.TABLE_NAME + "\n" + "(" + MyPostRequestDBColumnNames.STATUS + ",\n" + MyPostRequestDBColumnNames.POST_ID + ",\n" + MyPostRequestDBColumnNames.REQUEST_OWNER + ")\n" + "VALUES\n" + "(" + "'PENDING',\n" + post_id + "," + user_id + ");\n";

        logger.info(query);
        return query;
    }

    @Override
    public String getPostOwnerDetails(int post_id) {
        String query = "SELECT p." + PostDbColumnNames.TITLE + " as postTitle, u." + UserDbColumnNames.USERNAME + " as postOwnerEmail, u." + UserDbColumnNames.FIRSTNAME + " as postOwnerFirstName, u." + UserDbColumnNames.LASTNAME + " as postOwnerLastName from " + PostDbColumnNames.TABLE_NAME + " p \n" + "JOIN " + UserDbColumnNames.TABLE_NAME + " u on p." + PostDbColumnNames.OWNER + " = u." + UserDbColumnNames.ID + "\n" + "WHERE p." + PostDbColumnNames.ID + " = " + post_id + ";";

        logger.info(query);
        return query;
    }

    @Override
    public String updateRequestStatus(int requestId, PostRequestStatus postRequestStatus) {
        String query = "update " + MyPostRequestDBColumnNames.TABLE_NAME + " set " + MyPostRequestDBColumnNames.STATUS + " = '" + postRequestStatus.toString() + "'" + " where " + MyPostRequestDBColumnNames.ID + " = " + requestId + ";";
        logger.info(query);
        return query;
    }

    @Override
    public String getPostRequesterDetails(int request_id) {
        String query = "SELECT * from " + MyPostRequestDBColumnNames.TABLE_NAME + " pr \n" + "JOIN " + UserDbColumnNames.TABLE_NAME + " u on pr." + MyPostRequestDBColumnNames.REQUEST_OWNER + " = u." + UserDbColumnNames.ID + " \n" + "JOIN " + PostDbColumnNames.TABLE_NAME + " p on pr." + MyPostRequestDBColumnNames.POST_ID + " = p." + PostDbColumnNames.ID + " \n" + "WHERE pr." + MyPostRequestDBColumnNames.ID + " = " + request_id + ";";

        logger.info(query);
        return query;
    }

    @Override
    public String getMyRequestByUserId(int userid) {
        String query = "SELECT p." + PostDbColumnNames.TITLE + " as postTitle, " + MyPostRequestDBColumnNames.STATUS + ", post_owner." + UserDbColumnNames.FIRSTNAME + " as firstNameCreator, post_owner." + UserDbColumnNames.LASTNAME + " lastNameCreator " + "FROM " + MyPostRequestDBColumnNames.TABLE_NAME + " pr " + "JOIN " + PostDbColumnNames.TABLE_NAME + " p on pr." + MyPostRequestDBColumnNames.POST_ID + " = p." + PostDbColumnNames.ID + " JOIN " + UserDbColumnNames.TABLE_NAME + " u on pr." + MyPostRequestDBColumnNames.REQUEST_OWNER + " = u." + UserDbColumnNames.ID + " JOIN " + UserDbColumnNames.TABLE_NAME + " post_owner on post_owner." + UserDbColumnNames.ID + " = p." + PostDbColumnNames.OWNER + " WHERE u." + UserDbColumnNames.ID + " = " + userid + ";";

        logger.info(query);
        return query;
    }
}