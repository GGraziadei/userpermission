CREATE VIEW IF NOT EXISTS USER_JOIN_PERMISSION AS (
 SELECT P.PERMISSION_ID , U.USER_ID , U.FIRST_NAME , U.LAST_NAME, U.FISCAL_CODE, U.EMAIL , U.PHONE_NUMBER , P.TS_START , P.TS_END
 FROM up_user U , up_permission P
 WHERE U.USER_ID = P.USER_ID
);