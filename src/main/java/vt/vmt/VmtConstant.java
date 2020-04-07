package vt.vmt;

/**
 * @author vate
 */
public class VmtConstant {
    /**
     * the prefix of all api path
     */
    public static final String URI_PREFIX_VMT = "vmt";
    /**
     * the file separate '/'
     */
    public static final String SUFFIX_SLASH = "/";
    /**
     * the file separate ‘\’
     */
    public static final String SUFFIX_BACKSLASH = "\\";

    public static final String ROOT_PACKAGE_LOG = "vt.vmt.log";

    public static final String ROOT_PACKAGE_STATUS = "vt.vmt.status";

    public static final String ROOT_PACKAGE_INDEX = "vt.vmt.index";

    public static final String MODULE_NAME_LOG = "log";

    public static final String MODULE_NAME_STATUS = "status";

    public static final int BYTE_SIZE_MB = 1024 * 1024;

    public static final int BYTE_SIZE_KB = 1024;

    /**
     * the default line number when access log file at first in preview page
     */
    public static final int LOG_PREVIEW_DEFAULT_START_LINE = -1;
    /**
     * the max gettable log lines of per access in preview page
     */
    public static final int LOG_PREVIEW_DEFAULT_MAX_LINE = 10;

}
