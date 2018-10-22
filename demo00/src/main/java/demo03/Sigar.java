package demo03;

public class Sigar {

	class CpuPerc {
		private String combined;
		private String user;
		private String sys;
		private String wait;
		private String idle;

		public String getCombined() {
			return combined;
		}

		public void setCombined(String combined) {
			this.combined = combined;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getSys() {
			return sys;
		}

		public void setSys(String sys) {
			this.sys = sys;
		}

		public String getWait() {
			return wait;
		}

		public void setWait(String wait) {
			this.wait = wait;
		}

		public String getIdle() {
			return idle;
		}

		public void setIdle(String idle) {
			this.idle = idle;
		}
	}

	class Mem {
		private Long total;
		private Long used;
		private Long free;

		public Long getTotal() {
			return total;
		}

		public void setTotal(Long total) {
			this.total = total;
		}

		public Long getUsed() {
			return used;
		}

		public void setUsed(Long used) {
			this.used = used;
		}

		public Long getFree() {
			return free;
		}

		public void setFree(Long free) {
			this.free = free;
		}
	}

	private CpuPerc cpuPerc;

	private Mem mem;

	public CpuPerc getCpuPerc() {
		return cpuPerc;
	}

	public void setCpuPerc(CpuPerc cpuPerc) {
		this.cpuPerc = cpuPerc;
	}

	public Mem getMem() {
		return mem;
	}

	public void setMem(Mem mem) {
		this.mem = mem;
	}
}
