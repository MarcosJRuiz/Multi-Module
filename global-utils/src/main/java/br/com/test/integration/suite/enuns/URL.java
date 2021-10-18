package br.com.test.integration.suite.enuns;

/**
 * Classe para definir o dispositivo que executara o teste.
 * 
 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
 */
public enum URL {

/* Ambiente Produtivo Android*/
	ANDROID_VAREJO_CLARO("ANDROID_VAREJO_CLARO", "http://10.100.9.91:4726/wd/hub", 8106, "Packge_bunbleID", "Activity"),
	ANDROID_VAREJO_VIVO("ANDROID_VAREJO_VIVO", "http://10.100.9.91:4727/wd/hub", 8107, "Packge_bunbleID", "Activity"),
	ANDROID_LIGHT_OI("ANDROID_LIGHT_OI", "http://10.100.9.91:4722/wd/hub", 8102, "Packge_bunbleID.applight", "Activity"),
	ANDROID_LIGHT_VIVO("ANDROID_LIGHT_VIVO", "http://10.100.9.91:4723/wd/hub", 8103, "Packge_bunbleID.applight", "Activity"),
	ANDROID_EMPRESA_TIM("ANDROID_EMPRESA_TIM", "http://10.100.9.91:4724/wd/hub", 8104, "Packge_bunbleIDs", "Activity"),
	ANDROID_EMPRESA_CLARO("ANDROID_EMPRESA_CLARO", "http://10.100.9.91:4725/wd/hub", 8105, "Packge_bunbleIDs", "Activity"),
	ANDROID_ION("ANDROID_ION", "http://10.100.9.91:4728/wd/hub", 8108, "Packge_bunbleID", "Activity"),

/* Ambiente de Desenvolvimento Android */
	ANDROID_DEV_LIGHT("DEV_LIGHT", "http://10.100.9.90:4740/wd/hub", 8120, "Packge_bunbleID.applight", "Activity"),
	ANDROID_DEV_VAREJO("DEV_VAREJO", "http://10.100.9.90:4740/wd/hub", 8120, "Packge_bunbleID", "Activity"),
	ANDROID_DEV_PJ("DEV_PJ", "http://10.100.9.90:4740/wd/hub", 8120, "Packge_bunbleIDs", "Activity"),
	ANDROID_DEV_ION("DEV_ION", "http://10.100.9.90:4740/wd/hub", 8120, "Packge_bunbleID", "Activity"),

/* Ambiente Produtivo iOS*/     //bundleId
	IOS_PF("IOS_PF", "http://10.100.9.94:4721/wd/hub", 8101, "Packge_bunbleID", "T39VNS6BSR"),
	IOS_PJ("IOS_PJ", "http://10.100.9.94:4722/wd/hub", 8102, "Packge_bunbleID", "T39VNS6BSR"),
	IOS_ION("IOS_ION", "http://10.100.9.94:4723/wd/hub", 8103, "Packge_bunbleID", "T39VNS6BSR"),

/* Ambiente de Desenvolvimento iOS */
	IOS_DEV_ION("iDEV_ION", "http://10.100.9.90:4740/wd/hub", 8120, "Packge_bunbleID", "T4AU3N4VG4"),
	IOS_DEV_PJ("iDEV_EMPRESA", "http://10.100.9.90:4740/wd/hub", 8120, "Packge_bunbleID", "T4AU3N4VG4"),
	IOS_DEV_VAREJO("iDEV_VAREJO", "http://10.100.9.90:4740/wd/hub", 8120, "Packge_bunbleID", "T4AU3N4VG4"),

/* Browser Produtivo */
	ITOKEN_EMPRESA("ITOKEN_EMPRESA", "http://10.100.9.91:4720/wd/hub", 8100, "Packge_bunbleIDs", "Activity"),
	ITOKEN_VAREJO("ITOKEN_VAREJO", "http://10.100.9.91:4721/wd/hub", 8101, "Packge_bunbleID", "Activity"),
	BROWSER("BROWSER", "https://www.itau.com.br", 0, "", "");



	private final String url;
	private final String nome;
	private final String packge;
	private final String activity;
	private final int port;
	private final String bundleId;
	private final String xcodeOrgId;


	URL(String nome, String url, int port, String packge_bundleId, String activity_xcodeOrgId) {
		this.url = url;
		this.nome = nome;
		this.port = port;
		this.packge = packge_bundleId;
		this.activity = activity_xcodeOrgId;
		this.bundleId = packge_bundleId;
		this.xcodeOrgId = activity_xcodeOrgId;
	}

	public String nome() {
		return nome;
	}

	public String url() {
		return url;
	}

	public int port() {
		return port;
	}

	public String packge() {
		return packge;
	}

	public String activity() {
		return activity;
	}

	public String bundleId() {
		return bundleId;
	}

	public String xcodeOrgId() {
		return xcodeOrgId;
	}

}