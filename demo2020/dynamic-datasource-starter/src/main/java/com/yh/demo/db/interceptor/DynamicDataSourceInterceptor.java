package com.yh.demo.db.interceptor;

import com.yh.demo.db.dynamic.MyDynamicDataSourceContextHolder;
import com.yh.demo.db.dynamic.MyDynamicRoutingDataSource;
import com.yh.demo.db.model.entity.SysDataSource;
import com.yh.demo.db.service.DataSourceService;
import com.yh.demo.db.service.SysDataSourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * 拦截器作用，切换数据库源
 * 业务服务引入，相关类需要注入容器，再注册MVC
 *
 * @author yanghan
 * @return
 * @date 2021/2/3
 */
@Slf4j
@AllArgsConstructor
public class DynamicDataSourceInterceptor implements HandlerInterceptor {

    private final SysDataSourceService sysDataSourceService;
    private final DataSourceService dataSourceService;
    private final MyDynamicRoutingDataSource myDynamicRoutingDataSource;

    private static final String HEADER_TENANT_ID = "yh-tenant-id";
    private static final String HEADER_SUBSYSTEM_ID = "yh-subsystem-id";

    /**
     * 从token中解析租户ID和系统ID
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return return
     * @throws Exception Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SysDataSource sysDataSource = null;

        String subSystemId = request.getHeader(HEADER_SUBSYSTEM_ID);
        if (!StringUtils.isEmpty(subSystemId)) {
            log.info("请求头中获取HEADER_SUBSYSTEM_ID");
            sysDataSource = sysDataSourceService.getSysDataSourceBySubSystemId(subSystemId);
        }

        if (null == sysDataSource) {
            String tenantId = request.getHeader(HEADER_TENANT_ID);
            if (!StringUtils.isEmpty(tenantId)) {
                log.info("请求头Header中获取HEADER_TENANT_ID");
                sysDataSource = sysDataSourceService.getSysDataSourceByTenantId(tenantId);
            }
        }

        if (null == sysDataSource) {
            log.info("拦截器【动态数据库源】，没有切换数据源！");
            return true;
        }

        this.changeDataSource(sysDataSource);
        log.info("拦截器【动态数据库源】，成功切换数据源！当前是【{}】", sysDataSource.getKey_());
        return true;
    }

    private void changeDataSource(SysDataSource sysDataSource) {
        DataSource dataSource = dataSourceService.toDataSource(sysDataSource);
        //添加数据源
        myDynamicRoutingDataSource.addDataSource(sysDataSource.getKey_(), dataSource);
        MyDynamicDataSourceContextHolder.setDataSourceType(sysDataSource.getKey_());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
