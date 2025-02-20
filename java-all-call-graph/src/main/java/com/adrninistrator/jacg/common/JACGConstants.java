package com.adrninistrator.jacg.common;

/**
 * @author adrninistrator
 * @date 2021/6/17
 * @description:
 */

public class JACGConstants {

    public static final String[] TABLE_COLUMNS_CLASS_NAME = new String[]{
            DC.CN_FULL_NAME,
            DC.CN_SIMPLE_NAME};

    public static final String[] TABLE_COLUMNS_METHOD_ANNOTATION = new String[]{
            DC.MA_METHOD_HASH,
            DC.MA_ANNOTATION_NAME,
            DC.MA_FULL_METHOD,
    };

    public static final String[] TABLE_COLUMNS_METHOD_CALL = new String[]{
            DC.MC_ID,
            DC.MC_CALL_TYPE,
            DC.MC_ENABLED,
            DC.MC_CALLER_JAR_NUM,
            DC.MC_CALLER_METHOD_HASH,
            DC.MC_CALLER_FULL_METHOD,
            DC.MC_CALLER_METHOD_NAME,
            DC.MC_CALLER_FULL_CLASS_NAME,
            DC.MC_CALLER_CLASS_NAME,
            DC.MC_CALLER_LINE_NUM,
            DC.MC_CALLEE_METHOD_HASH,
            DC.MC_CALLEE_FULL_METHOD,
            DC.MC_CALLEE_METHOD_NAME,
            DC.MC_CALLEE_FULL_CLASS_NAME,
            DC.MC_CALLEE_CLASS_NAME
    };

    public static final String[] TABLE_COLUMNS_JAR_INFO = new String[]{
            DC.JI_JAR_NUM,
            DC.JI_JAR_TYPE,
            DC.JI_JAR_PATH_HASH,
            DC.JI_JAR_FULL_PATH,
            DC.JI_LAST_MODIFIED,
            DC.JI_JAR_HASH
    };

    public static final String[] TABLE_COLUMNS_EXTENDED_DATA = new String[]{
            DC.ED_CALL_ID,
            DC.ED_DATA_TYPE,
            DC.ED_DATA_VALUE
    };

    public static final String[] TABLE_COLUMNS_MANUAL_ADD_EXTENDED_DATA = new String[]{
            DC.MAED_CALLER_FULL_METHOD,
            DC.MAED_CALLEE_FULL_METHOD,
            DC.MAED_CALLEE_SEQ_IN_CALLER,
            DC.MAED_DATA_TYPE,
            DC.MAED_DATA_VALUE
    };

    public static final String DIR_CONFIG = "~jacg_config";
    public static final String DIR_OUTPUT_GRAPH_FOR_CALLEE = "~jacg_o_ee";
    public static final String DIR_OUTPUT_GRAPH_FOR_CALLER = "~jacg_o_er";
    public static final String DIR_SQL = "~jacg_sql";
    public static final String DIR_METHODS = "methods";
    public static final String DIR_FIND_KEYWORD_ = "~find_kw";
    public static final String DIR_KEYWORD_CONF = "~jacg_find_keyword";
    public static final String DIR_EXTENSIONS = "~jacg_extensions";

    public static final String FILE_CONFIG = "config.properties";

    public static final String FILE_EXTENSIONS_CODE_PARSER = "code_parser.properties";
    public static final String FILE_EXTENSIONS_EXTENDED_DATA_ADD = "extended_data_add.properties";
    public static final String FILE_EXTENSIONS_EXTENDED_DATA_SUPPLEMENT = "extended_data_supplement.properties";

    public static final String FILE_IN_ALLOWED_CLASS_PREFIX = "i_allowed_class_prefix.properties";

    public static final String FILE_OUT_GRAPH_FOR_CALLEE_CLASS_NAME = "o_g4callee_class_name.properties";
    public static final String FILE_OUT_GRAPH_FOR_CALLER_ENTRY_METHOD = "o_g4caller_entry_method.properties";
    public static final String FILE_OUT_GRAPH_FOR_CALLER_ENTRY_METHOD_IGNORE_PREFIX = "o_g4caller_entry_method_ignore_prefix.properties";
    public static final String FILE_OUT_GRAPH_FOR_CALLER_IGNORE_CLASS_KEYWORD = "o_g4caller_ignore_class_keyword.properties";
    public static final String FILE_OUT_GRAPH_FOR_CALLER_IGNORE_FULL_METHOD_PREFIX = "o_g4caller_ignore_full_method_prefix.properties";
    public static final String FILE_OUT_GRAPH_FOR_CALLER_IGNORE_METHOD_PREFIX = "o_g4caller_ignore_method_prefix.properties";

    public static final String FILE_SQL_CLASS_NAME = "class_name.sql";
    public static final String FILE_SQL_METHOD_ANNOTATION = "method_annotation.sql";
    public static final String FILE_SQL_METHOD_CALL = "method_call.sql";
    public static final String FILE_SQL_JAR_INFO = "jar_info.sql";
    public static final String FILE_SQL_EXTENDED_DATA = "extended_data.sql";
    public static final String FILE_SQL_MANUAL_ADD_EXTENDED_DATA = "manual_add_extended_data.sql";

    public static final String FILE_FIND_KEYWORD_4CALLEE = "find_keyword_4callee.properties";
    public static final String FILE_FIND_KEYWORD_4CALLER = "find_keyword_4caller.properties";

    public static final String APPNAME_IN_SQL = "{appName}";

    public static final String KEY_APPNAME = "app.name";
    public static final String KEY_CALL_GRAPH_JAR_LIST = "call.graph.jar.list";
    public static final String KEY_INPUT_IGNORE_OTHER_PACKAGE = "input.ignore.other.package";
    public static final String KEY_CALL_GRAPH_OUTPUT_DETAIL = "call.graph.output.detail";
    public static final String KEY_THREAD_NUM = "thread.num";
    public static final String KEY_SHOW_METHOD_ANNOTATION = "show.method.annotation";
    public static final String KEY_GEN_COMBINED_OUTPUT = "gen.combined.output";
    public static final String KEY_SHOW_CALLER_LINE_NUM = "show.caller.line.num";
    public static final String KEY_GEN_UPWARDS_METHODS_FILE = "gen.upwards.methods.file";
    public static final String KEY_IGNORE_DUP_CALLEE_IN_ONE_CALLER = "ignore.dup.callee.in.one.caller";

    public static final String KEY_DB_USE_H2 = "db.use.h2";
    public static final String KEY_DB_H2_FILE_PATH = "db.h2.file.path";
    public static final String KEY_DB_DRIVER_NAME = "db.driver.name";
    public static final String KEY_DB_URL = "db.url";
    public static final String KEY_DB_USERNAME = "db.username";
    public static final String KEY_DB_PASSWORD = "db.password";

    public static final String CONFIG_OUTPUT_DETAIL_1 = "1";
    public static final String CONFIG_OUTPUT_DETAIL_2 = "2";
    public static final String CONFIG_OUTPUT_DETAIL_3 = "3";

    // 以上开头字符串长度
    public static final int FILE_KEY_PREFIX_LENGTH = 2;

    public static final String SQL_KEY_CN_QUERY_DUPLICATE_CLASS = "cn_query_duplicate_class";
    public static final String SQL_KEY_CN_QUERY_SIMPLE_CLASS = "cn_query_simple_class";
    public static final String SQL_KEY_CN_QUERY_FULL_CLASS = "cn_query_full_class";
    public static final String SQL_KEY_CN_UPDATE_SIMPLE_2_FULL = "cn_update_simple_2_full";

    public static final String SQL_KEY_MA_QUERY_METHOD_ANNOTATION = "ma_query_method_annotation";

    public static final String SQL_KEY_MC_QUERY_CLASS_EXISTS = "mc_query_class_exists";
    public static final String SQL_KEY_MC_QUERY_CALLER_FULL_CLASS = "mc_query_caller_full_class";
    public static final String SQL_KEY_MC_QUERY_TOP_METHOD = "mc_query_top_method";
    public static final String SQL_KEY_MC_QUERY_ONE_CALLEE = "mc_query_one_callee";
    public static final String SQL_KEY_MC_QUERY_ONE_CALLEE_CHECK_LINE_NUM = "mc_query_one_callee_cln";
    public static final String SQL_KEY_MC_QUERY_CALLEE_ALL_METHODS = "mc_query_callee_all_methods";
    public static final String SQL_KEY_MC_QUERY_ONE_CALLER1 = "mc_query_one_caller1";
    public static final String SQL_KEY_MC_QUERY_ONE_CALLER2 = "mc_query_one_caller2";
    public static final String SQL_KEY_MC_QUERY_NOTICE_INFO = "mc_query_notice_info";
    public static final String SQL_KEY_MC_QUERY_ALL_CALLER = "mc_query_all_caller";
    public static final String SQL_KEY_MC_QUERY_CALLEE_SEQ_IN_CALLER = "mc_query_callee_in_caller_seq";
    public static final String SQL_KEY_MC_QUERY_CALLEE_BY_ID = "mc_query_callee_by_id";
    public static final String SQL_KEY_MC_QUERY_IMPL_METHODS = "mc_query_impl_methods";

    public static final String SQL_KEY_JI_QUERY_JAR_INFO = "ji_query_jar_info";

    public static final String SQL_KEY_ED_QUERY_EXTENDED_DATA = "ed_query_extended_data";

    public static final String SQL_KEY_MAED_QUERY = "ed_query_MAED";
    public static final String SQL_KEY_MAED_QUERY_IGNORE_CALLER = "ed_query_MAED_ignore_caller";

    public static final String SQL_KEY_INSERT_CLASS_NAME = "insert_class_name";
    public static final String SQL_KEY_INSERT_METHOD_ANNOTATION = "insert_method_annotation";
    public static final String SQL_KEY_INSERT_METHOD_CALL = "insert_method_call";
    public static final String SQL_KEY_INSERT_JAR_INFO = "insert_jar_info";
    public static final String SQL_KEY_INSERT_EXTENDED_DATA = "insert_extended_data";

    public static final String SQL_KEY_SQL_MODE_SELECT = "sql_mode_select";
    public static final String SQL_KEY_SQL_MODE_SET = "sql_mode_set";

    public static final String SQL_VALUE_MAED_CALLER_FULL_METHOD_ALL = "*";

    public static final String SQL_CREATE_TABLE_HEAD = "CREATE TABLE if not exists";
    public static final int SQL_CREATE_TABLE_HEAD_LENGTH = SQL_CREATE_TABLE_HEAD.length();

    public static final String FLAG_DOT = ".";
    public static final String FLAG_COLON = ":";
    public static final String FLAG_LEFT_BRACKET = "(";
    public static final String FLAG_RIGHT_BRACKET = ")";
    public static final String FLAG_LEFT_PARENTHESES = "[";
    public static final String FLAG_RIGHT_PARENTHESES = "]";
    public static final String FLAG_SPACE = " ";
    public static final String FLAG_HASHTAG = "#";
    public static final String FLAG_AT = "@";
    public static final String FLAG_MINUS = "-";
    public static final String FLAG_TAB = "\t";
    public static final String FLAG_COMMA_WITH_SPACE = ", ";

    public static final String EXT_TXT = ".txt";
    public static final String EXT_MD = ".md";

    public static final String NEW_LINE = "\n";

    public static final int BATCH_SIZE = 1000;
    public static final int MAX_THREAD_NUM = 100;
    public static final int NOTICE_LINE_NUM = 500;

    public static final String TABLE_PREFIX_CLASS_NAME = "class_name_";
    public static final String TABLE_PREFIX_METHOD_ANNOTATION = "method_annotation_";
    public static final String TABLE_PREFIX_METHOD_CALL = "method_call_";
    public static final String TABLE_PREFIX_JAR_INFO = "jar_info_";
    public static final String TABLE_PREFIX_EXTENDED_DATA = "extended_data_";
    public static final String TABLE_PREFIX_MANUAL_ADD_EXTENDED_DATA = "manual_add_extended_data_";

    public static final String OUTPUT_SPLIT_FLAG = "  ";

    public static final String COMBINE_FILE_NAME_PREFIX = "~all";
    public static final String COMBINE_FILE_NAME_4_CALLEE = "-4callee";
    public static final String COMBINE_FILE_NAME_4_CALLER = "-4caller";

    public static final int THREAD_POOL_MAX_QUEUE_SIZE = 100;
    public static final int THREAD_POOL_WAIT_QUEUE_SIZE = 80;

    public static final String CALLEE_FLAG_ENTRY = FLAG_TAB + "!entry!";
    public static final String CALL_FLAG_CYCLE = FLAG_TAB + "!cycle[%d]!";
    public static final String CALL_FLAG_EXTENDED_DATA_NO_TAB = "!ext_data!";
    public static final String CALL_FLAG_EXTENDED_DATA_MANUAL_ADD_NO_TAB = "!ext_data_ma!";
    public static final String CALL_FLAG_EXTENDED_DATA = FLAG_TAB + CALL_FLAG_EXTENDED_DATA_NO_TAB;
    public static final String CALL_FLAG_EXTENDED_DATA_MANUAL_ADD = FLAG_TAB + CALL_FLAG_EXTENDED_DATA_MANUAL_ADD_NO_TAB;

    public static final String MYSQL_FLAG = "mysql";
    public static final String MYSQL_REWRITEBATCHEDSTATEMENTS = "rewriteBatchedStatements=true";
    public static final String MYSQL_ONLY_FULL_GROUP_BY = "ONLY_FULL_GROUP_BY";

    public static final int METHOD_CALL_ID_START = 0;

    public static final int NO_CYCLE_CALL_FLAG = -1;

    // 是否在结果文件中写入配置信息
    public static final String PROPERTY_WRITE_CONFIG_IN_RESULT = "write.config";
    // 生成结果文件根目录
    public static final String PROPERTY_OUTPUT_ROOT_PATH = "output.root.path";
    // 跳过检查Jar包文件是否有更新
    public static final String PROPERTY_SKIP_CHECK_JAR_FILE_UPDATED = "skip.check.jar.file.updated";

    public static final int ENABLED = 1;
    public static final int DISABLED = 0;

    public static final String NOTICE_MULTI_ITF_MD = "~notice_multi_ITF.md";
    public static final String NOTICE_MULTI_SCC_MD = "~notice_multi_SCC.md";
    public static final String NOTICE_DISABLED_ITF_MD = "~notice_disabled_ITF.md";
    public static final String NOTICE_DISABLED_SCC_MD = "~notice_disabled_SCC.md";

    public static final int LINE_NUM_NONE = -1;

    public static final String DATA_TYPE_JUMP_MULTI_IMPL = "JUMP_MULTI_IMPL";

    public static final String EMPTY_FILE_FLAG = "-empty";

    public static final String H2_PROTOCOL = "jdbc:h2:file:";
    public static final String H2_SCHEMA = "jacg";
    public static final String H2_FILE_EXT = ".mv.db";

    public static final String JAR_TYPE_JAR = "jar";
    public static final String JAR_TYPE_DIR = "dir";

    private JACGConstants() {
        throw new IllegalStateException("illegal");
    }
}
