package alternatives;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.inject.Produces;

import annotationTest.AlternativeTest;
import br.ufpe.exemploprojeto.DAO.jpa.util.JPAUtil;

public class AlternativeProduces implements Serializable{
	private static final long serialVersionUID = -7752656729863886259L;

	@Produces @AlternativeTest
	public Properties createProperties() throws IOException{
		Properties props = new Properties();
		try {
			props.load(JPAUtil.class.getClassLoader().getResourceAsStream("application-unitTest.properties"));
			return props;
		}catch (IOException e){
			e.printStackTrace();
			throw e;
		}
	}
	
}
