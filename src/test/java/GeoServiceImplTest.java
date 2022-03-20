import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

public class GeoServiceImplTest {
    @Test
    void test_byIp() {
        GeoServiceImpl geoServiceImpl = new GeoServiceImpl();
        String ip = "172.";
        Country expected = Country.RUSSIA;
        Country preference = geoServiceImpl.byIp(ip).getCountry();
        Assertions.assertEquals(expected, preference);
    }
}