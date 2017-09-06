package com.audit.app.Services.ServiceImpl;

import java.lang.management.ManagementFactory;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.audit.app.Services.IService.ICPULogService;
import com.sun.management.OperatingSystemMXBean;

@Service
public class CPULogService implements ICPULogService {
	private static Logger LOG = Logger.getLogger(CPULogService.class);

	@Override
	public double getCPULoad() {
		double val = 0;
		try {
			OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory
					.getOperatingSystemMXBean();
			val = Math.round(operatingSystemMXBean.getSystemCpuLoad() * 100);// operatingSystemMXBean.getProcessCpuLoad();
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
		return val;
	}

}
