package ohtu.verkkokauppa;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    Kauppa kauppa;

	@Test
	public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaan() {
    	// luodaan ensin mock-oliot
    	Pankki pankki = mock(Pankki.class);

    	Viitegeneraattori viite = mock(Viitegeneraattori.class);
	    // määritellään että viitegeneraattori palauttaa viitten 42
    	when(viite.uusi()).thenReturn(42);

	    Varasto varasto = mock(Varasto.class);
    	// määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
    	when(varasto.saldo(1)).thenReturn(10);
	    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    	// sitten testattava kauppa
	    Kauppa k = new Kauppa(varasto, pankki, viite);

    	// tehdään ostokset
	    k.aloitaAsiointi();
    	k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
	    k.tilimaksu("pekka", "12345");

    	// sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
	}

    @Test
    public void ostettaessaEriTuotteetPankinMetodiaTilisiirtoKutsutaanOikein() {
    	// luodaan ensin mock-oliot
    	Pankki pankki = mock(Pankki.class);

    	Viitegeneraattori viite = mock(Viitegeneraattori.class);
	    // määritellään että viitegeneraattori palauttaa viitten 42
    	when(viite.uusi()).thenReturn(42);

	    Varasto varasto = mock(Varasto.class);
    	// määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
    	when(varasto.saldo(1)).thenReturn(10);
	    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "jaffa", 10));

    	// sitten testattava kauppa
	    Kauppa k = new Kauppa(varasto, pankki, viite);

    	// tehdään ostokset
	    k.aloitaAsiointi();
    	k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);
	    k.tilimaksu("pekka", "12345");

    	// sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(15));
    }

    @Test
    public void ostettaessaSamatTuotteetPankinMetodiaTilisiirtoKutsutaanOikein() {
    	// luodaan ensin mock-oliot
    	Pankki pankki = mock(Pankki.class);

    	Viitegeneraattori viite = mock(Viitegeneraattori.class);
	    // määritellään että viitegeneraattori palauttaa viitten 42
    	when(viite.uusi()).thenReturn(42);

	    Varasto varasto = mock(Varasto.class);
    	// määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
    	when(varasto.saldo(1)).thenReturn(10);
	    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    	// sitten testattava kauppa
	    Kauppa k = new Kauppa(varasto, pankki, viite);

    	// tehdään ostokset
	    k.aloitaAsiointi();
    	k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(1);
	    k.tilimaksu("pekka", "12345");

    	// sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(10));
    }

    @Test
    public void ostettaessaLoppunutTuotePankinMetodiaTilisiirtoKutsutaanOikein() {
    	// luodaan ensin mock-oliot
    	Pankki pankki = mock(Pankki.class);

    	Viitegeneraattori viite = mock(Viitegeneraattori.class);
	    // määritellään että viitegeneraattori palauttaa viitten 42
    	when(viite.uusi()).thenReturn(42);

	    Varasto varasto = mock(Varasto.class);
    	// määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
    	when(varasto.saldo(1)).thenReturn(10);
	    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "jaffa", 10));

    	// sitten testattava kauppa
	    Kauppa k = new Kauppa(varasto, pankki, viite);

    	// tehdään ostokset
	    k.aloitaAsiointi();
    	k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);
	    k.tilimaksu("pekka", "12345");

    	// sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
    }

    @Test
    public void aloitaAsiointiKutsuminenNollaaEdellisenOstoksenTiedot() {
    	// luodaan ensin mock-oliot
    	Pankki pankki = mock(Pankki.class);

    	Viitegeneraattori viite = mock(Viitegeneraattori.class);
	    // määritellään että viitegeneraattori palauttaa viitten 42
    	when(viite.uusi()).thenReturn(42);

	    Varasto varasto = mock(Varasto.class);
    	// määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
    	when(varasto.saldo(1)).thenReturn(10);
	    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    	// sitten testattava kauppa
	    Kauppa k = new Kauppa(varasto, pankki, viite);

    	// tehdään ostokset
	    k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
	    k.tilimaksu("pekka", "12345");

        verify(pankki, times(1)).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
	    k.tilimaksu("pekka", "12345");

        verify(pankki, times(2)).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
    }

    @Test
    public void kauppaPyytaaUudenViitteenMaksulle() {
    	// luodaan ensin mock-oliot
    	Pankki pankki = mock(Pankki.class);

    	Viitegeneraattori viite = mock(Viitegeneraattori.class);
	    // määritellään että viitegeneraattori palauttaa viitten 42
    	when(viite.uusi()).thenReturn(42);

	    Varasto varasto = mock(Varasto.class);
    	// määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
    	when(varasto.saldo(1)).thenReturn(10);
	    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    	// sitten testattava kauppa
	    Kauppa k = new Kauppa(varasto, pankki, viite);

    	// tehdään ostokset
	    k.aloitaAsiointi();
    	k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
	    k.tilimaksu("pekka", "12345");

        verify(viite, times(1)).uusi();

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        verify(viite, times(2)).uusi();
    }

    @Test
    public void ostoksienPoistaminenKoristaToimiiJaKauppaVeloittaaOikein() {
    	// luodaan ensin mock-oliot
    	Pankki pankki = mock(Pankki.class);

    	Viitegeneraattori viite = mock(Viitegeneraattori.class);
	    // määritellään että viitegeneraattori palauttaa viitten 42
    	when(viite.uusi()).thenReturn(42);

	    Varasto varasto = mock(Varasto.class);
    	// määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
    	when(varasto.saldo(1)).thenReturn(10);
	    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    	// sitten testattava kauppa
	    Kauppa k = new Kauppa(varasto, pankki, viite);

    	// tehdään ostokset
	    k.aloitaAsiointi();
    	k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(1);
        k.poistaKorista(1);
	    k.tilimaksu("pekka", "12345");

    	// sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
    }
}
