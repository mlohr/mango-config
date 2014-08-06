mango-config
============

Mapper for (typesafe) Config to data-models (POJOs with setters)

Example
=======

    @Data
    public class Foo {
	    private String bar;
	    private int answer;
    }

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

Config-file
-----------
    foo1 {
    	bar = present
    	answer = 42
    }
    foos = [
    	{   bar = first
    		answer = 1
    	}
    	{   bar = second
    		answer = 2
    	}
    	{   bar = third
    		answer = 3
    	}
    ]
