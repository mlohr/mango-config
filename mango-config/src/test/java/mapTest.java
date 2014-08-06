import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import lombok.val;
import net.mloehr.mango.config.ConfigMapper;

import org.junit.Before;
import org.junit.Test;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class mapTest {

	private Config conf;
	
	@Before
	public void init() {
		conf = ConfigFactory.load();
	}

	@Test
	public void testMapObject() {
		val foo = new Foo();
		ConfigMapper.map(conf, "foo1", foo);
		assertThat(foo.getBar(), is("present"));
		assertThat(foo.getAnswer(), is(42));
	}

	@Test
	public void testMapList() {
		ArrayList<Foo> list = new ArrayList<Foo>();
		ConfigMapper.map(conf, "foos", list, Foo.class);		
		assertThat(list.get(0).getBar(), is("first"));
		assertThat(list.get(2).getAnswer(), is(3));
	}
}
