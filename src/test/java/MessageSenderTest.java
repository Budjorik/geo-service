import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.BaseStubbing;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;
import java.util.HashMap;
import java.util.Map;

public class MessageSenderTest {
    /** Поверка, что MessageSenderImpl всегда отправляет только русский текст,
     * если ip относится к российскому сегменту адресов
     */
    @Test
    void test_get_russian_text_mockito () {
        String ip = "172.";
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Mockito.<Country>any()))
                .thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String preference = messageSender.send(headers);
        String expected = "Добро пожаловать";
        Assertions.assertEquals(expected, preference);
        System.out.println();
    }

    /** Поверка, что MessageSenderImpl всегда отправляет только английский текст,
     * если ip относится к американскому сегменту адресов
     */
    @Test
    void test_get_english_text_mockito () {
        String ip = "96.";
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(new Location("New York", Country.USA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Mockito.<Country>any()))
                .thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String preference = messageSender.send(headers);
        String expected = "Welcome";
        Assertions.assertEquals(expected, preference);
        System.out.println();
    }
}