package ch.unibe.ese.team1.test.testData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import ch.unibe.ese.team1.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.AdPicture;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.UserDao;

import static ch.unibe.ese.team1.model.Property.APARTMENT;
import static ch.unibe.ese.team1.model.Property.HOUSE;
import static ch.unibe.ese.team1.model.Property.STUDIO;

/** This inserts several ad elements into the database. */
@Service
public class AdTestDataSaver {

	@Autowired
	private AdDao adDao;
	@Autowired
	private UserDao userDao;

	@Transactional
	public void saveTestData() throws Exception {
		User bernerBaer = userDao.findByUsername("user@bern.com");
		User ese = userDao.findByUsername("ese@unibe.ch");
		User oprah = userDao.findByUsername("oprah@winfrey.com");
		User jane = userDao.findByUsername("jane@doe.com");

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");


        Date creationDate1 = formatter.parse("03.10.2014");
		Date creationDate2 = formatter.parse("11.10.2014");
		Date creationDate3 = formatter.parse("25.10.2014");
		Date creationDate4 = formatter.parse("02.11.2014");
		Date creationDate5 = formatter.parse("25.11.2013");
		Date creationDate6 = formatter.parse("01.12.2014");
		Date creationDate7 = formatter.parse("16.11.2014");
		Date creationDate8 = formatter.parse("27.11.2014");
		
		Date moveInDate1 = formatter.parse("15.12.2014");
		Date moveInDate2 = formatter.parse("21.12.2014");
		Date moveInDate3 = formatter.parse("01.01.2015");
		Date moveInDate4 = formatter.parse("15.01.2015");
		Date moveInDate5 = formatter.parse("01.02.2015");
		Date moveInDate6 = formatter.parse("01.03.2015");
		Date moveInDate7 = formatter.parse("15.03.2015");
		Date moveInDate8 = formatter.parse("16.02.2015");

		
		String roomDescription1 = "The room is a part of 3.5 rooms apartment completely renovated"
				+ "in 2010 at Kramgasse, Bern. The apartment is about 50 m2 on 1st floor."
				+ "Apt is equipped modern kitchen, hall and balcony. Near to shops and public"
				+ "transportation. Monthly rent is 500 CHF including charges. Internet + TV + landline"
				+ "charges are separate. If you are interested, feel free to drop me a message"
				+ "to have an appointment for a visit or can write me for any further information";


		Ad adBern = new Ad();
		adBern.setZipcode(3011);
		adBern.setMoveInDate(moveInDate1);
		adBern.setCreationDate(creationDate1);
		adBern.setPrice(145000);
		adBern.setSquareFootage(600);
		adBern.setNumberRooms(3.5f);
		adBern.setProperty(HOUSE);
		adBern.setSmokers(false);
		adBern.setAnimals(true);
		adBern.setRoomDescription(roomDescription1);
		adBern.setUser(bernerBaer);
		adBern.setTitle("Amazingly Amazing House!");
		adBern.setStreet("Kramgasse 22");
		adBern.setCity("Bern");
		adBern.setGarden(true);
		adBern.setBalcony(true);
		adBern.setCellar(true);
		adBern.setFurnished(true);
		adBern.setGarage(true);
		date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(1));
        adBern.setExpireDate(getTimedDate(10));
		adBern.setPremium(true);
        List<AdPicture> pictures = new ArrayList<>();
		pictures.add(createPicture(adBern, "/img/test/ad1_1.jpg"));
		pictures.add(createPicture(adBern, "/img/test/ad1_2.jpg"));
		pictures.add(createPicture(adBern, "/img/test/ad1_3.jpg"));
		adBern.setPictures(pictures);
		adDao.save(adBern);

		String studioDescription2 = "It is small house close to the"
				+ "university and the bahnhof. The lovely neighbourhood"
				+ "Langgasse makes it an easy place to feel comfortable."
				+ "The house is close to a Migross, Denner and the Coop."
				+ "The house is fully furnished. The"
				+ "house is also provided with a balcony. So if you want to"
				+ "have a private space this could totally be good place for you.";

		Ad adBern2 = new Ad();
		adBern2.setZipcode(3012);
		adBern2.setMoveInDate(moveInDate2);
		adBern2.setCreationDate(creationDate2);
		adBern2.setPrice(2000);
		adBern2.setSquareFootage(60);
        adBern2.setNumberRooms(2f);
		adBern2.setProperty(STUDIO);
		adBern2.setSmokers(false);
		adBern2.setAnimals(true);
		adBern2.setRoomDescription(studioDescription2);
		adBern2.setUser(ese);
		adBern2.setTitle("Chill studio in Bern!");
		adBern2.setStreet("Längassstr. 40");
		adBern2.setCity("Bern");
		adBern2.setGarden(false);
		adBern2.setBalcony(false);
		adBern2.setCellar(false);
		adBern2.setFurnished(false);
		adBern2.setGarage(false);
		adBern2.setPremium(true);
		date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(1));
		adBern2.setExpireDate(getTimedDate(4));
		pictures = new ArrayList<>();
		pictures.add(createPicture(adBern2, "/img/test/ad2_1.jpg"));
		pictures.add(createPicture(adBern2, "/img/test/ad2_2.jpg"));
		pictures.add(createPicture(adBern2, "/img/test/ad2_3.jpg"));
		adBern2.setPictures(pictures);
		adDao.save(adBern2);

		String studioDescription3 = " In the center of Gundeli (5 Min. away from the"
				+ "station, close to Tellplatz) there is a lovely house, covered with"
				+ "savage wine stocks, without any neighbours but with a garden. The"
				+ "house has two storey, on the first floor your new room is waiting"
				+ "for you. The house is totally equipped with everything a household "
				+ ": washing machine, kitchen, batroom, W-Lan...if you don´t have any"
				+ "furniture, don´t worry, I am sure, we will find something around"
				+ "the house. ";

		Ad adBasel = new Ad();
		adBasel.setZipcode(4051);
		adBasel.setMoveInDate(moveInDate3);
		adBasel.setCreationDate(creationDate3);
		adBasel.setPrice(0);
		adBasel.setSquareFootage(10);
        adBasel.setNumberRooms(1f);
		adBasel.setProperty(APARTMENT);
		adBasel.setSmokers(true);
		adBasel.setAnimals(false);
		adBasel.setRoomDescription(studioDescription3);
		adBasel.setUser(bernerBaer);
		adBasel.setTitle("Nice, bright studio in the center of Basel");
		adBasel.setStreet("Bruderholzstrasse 32");
		adBasel.setCity("Basel");
		adBasel.setGarden(false);
		adBasel.setBalcony(false);
		adBasel.setCellar(false);
		adBasel.setFurnished(false);
		adBasel.setGarage(false);
		adBasel.setExpireDate(getTimedDate(100000));
		pictures = new ArrayList<>();
		pictures.add(createPicture(adBasel, "/img/test/ad3_1.jpg"));
		pictures.add(createPicture(adBasel, "/img/test/ad3_2.jpg"));
		pictures.add(createPicture(adBasel, "/img/test/ad3_3.jpg"));
		adBasel.setPictures(pictures);
		adDao.save(adBasel);
		
		String studioDescription4 = "Flat with 5 rooms"
				+ "on the second floor. The bedroom is about 60 square meters"
				+ "with access to a nice balcony. In addition to the room, the"
				+ "flat has: a living room, a kitchen, a bathroom, a seperate WC,"
				+ "a storage in the basement, a balcony, a laundry room in the basement."
				+ "The bedroom is big and bright and has a nice parquet floor."
				+ "Possibility to keep some furnitures like the bed.";

		Ad adOlten = new Ad();
		adOlten.setZipcode(4600);
		adOlten.setMoveInDate(moveInDate4);
		adOlten.setCreationDate(creationDate4);
		adOlten.setPrice(0);
		adOlten.setSquareFootage(220);
        adOlten.setNumberRooms(5);
		adOlten.setProperty(HOUSE);
		adOlten.setSmokers(true);
		adOlten.setAnimals(false);
		adOlten.setRoomDescription(studioDescription4);
		adOlten.setUser(ese);
		adOlten.setTitle("Roommate wanted in Olten City");
		adOlten.setStreet("Zehnderweg 5");
		adOlten.setCity("Olten");
		adOlten.setGarden(false);
		adOlten.setBalcony(true);
		adOlten.setCellar(true);
		adOlten.setFurnished(true);
		adOlten.setGarage(false);
		adOlten.setExpireDate(getTimedDate(40));
		pictures = new ArrayList<>();
		pictures.add(createPicture(adOlten, "/img/test/ad4_1.png"));
		pictures.add(createPicture(adOlten, "/img/test/ad4_2.png"));
		pictures.add(createPicture(adOlten, "/img/test/ad4_3.png"));
		adOlten.setPictures(pictures);
		adDao.save(adOlten);

		String studioDescription5 = "Studio meublé au 3ème étage, comprenant"
				+ "une kitchenette entièrement équipée (frigo, plaques,"
				+ "four et hotte), une pièce à vivre donnant sur un balcon,"
				+ "une salle de bains avec wc. Cave, buanderie et site satellite"
				+ "à disposition.";

		Ad adNeuchâtel = new Ad();
		adNeuchâtel.setZipcode(2000);
		adNeuchâtel.setMoveInDate(moveInDate5);
		adNeuchâtel.setCreationDate(creationDate5);
		adNeuchâtel.setPrice(0);
		adNeuchâtel.setSquareFootage(40);
        adNeuchâtel.setNumberRooms(2.5f);
		adNeuchâtel.setProperty(STUDIO);
		adNeuchâtel.setSmokers(true);
		adNeuchâtel.setAnimals(false);
		adNeuchâtel.setRoomDescription(studioDescription5);
		adNeuchâtel.setUser(bernerBaer);
		adNeuchâtel.setTitle("BIG FAT STUDIO in Neuchâtel");
		adNeuchâtel.setStreet("Rue de l'Hôpital 11");
		adNeuchâtel.setCity("Neuchâtel");
		adNeuchâtel.setGarden(true);
		adNeuchâtel.setBalcony(false);
		adNeuchâtel.setCellar(true);
		adNeuchâtel.setFurnished(true);
		adNeuchâtel.setGarage(false);
		date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(10));
		adNeuchâtel.setExpireDate(getTimedDate(50000));
        pictures = new ArrayList<>();
		pictures.add(createPicture(adNeuchâtel, "/img/test/ad5_1.jpg"));
		pictures.add(createPicture(adNeuchâtel, "/img/test/ad5_2.jpg"));
		pictures.add(createPicture(adNeuchâtel, "/img/test/ad5_3.jpg"));
		adNeuchâtel.setPictures(pictures);
		adDao.save(adNeuchâtel);

		String studioDescription6 = "A place just for yourself in a very nice part of Biel."
				+ "A studio for 1-2 persons with a big balcony, bathroom, kitchen and furniture already there."
				+ "It's quiet and nice, very close to the old city of Biel.";

		Ad adBiel = new Ad();
		adBiel.setZipcode(2503);
		adBiel.setMoveInDate(moveInDate6);
		adBiel.setCreationDate(creationDate6);
		adBiel.setPrice(29000);
		adBiel.setSquareFootage(10);
        adBiel.setNumberRooms(3.5f);
		adBiel.setProperty(STUDIO);
		adBiel.setSmokers(true);
		adBiel.setAnimals(false);
		adBiel.setRoomDescription(studioDescription6);
		adBiel.setUser(ese);
		adBiel.setTitle("Direkt am Quai: hübsches Studio");
		adBiel.setStreet("Oberer Quai 12");
		adBiel.setCity("Biel/Bienne");
		adBiel.setGarden(false);
		adBiel.setBalcony(false);
		adBiel.setCellar(false);
		adBiel.setFurnished(false);
		adBiel.setGarage(false);
		date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(10));
		adBiel.setExpireDate(getTimedDate(100000));
		pictures = new ArrayList<>();
		pictures.add(createPicture(adBiel, "/img/test/ad6_1.png"));
		pictures.add(createPicture(adBiel, "/img/test/ad6_2.png"));
		pictures.add(createPicture(adBiel, "/img/test/ad6_3.png"));
		adBiel.setPictures(pictures);
		adDao.save(adBiel);
		
		
		String roomDescription7 = "The room is a part of 3.5 rooms apartment completely renovated"
				+ "in 2010 at Kramgasse, Bern. The apartment is about 50 m2 on 1st floor."
				+ "Apt is equipped modern kitchen, hall and balcony. Near to shops and public"
				+ "transportation. Monthly rent is 500 CHF including charges. Internet + TV + landline"
				+ "charges are separate. If you are interested, feel free to drop me a message"
				+ "to have an appointment for a visit or can write me for any further information";

		Ad adZurich = new Ad();
		adZurich.setZipcode(8000);
		adZurich.setMoveInDate(moveInDate7);
		adZurich.setCreationDate(creationDate7);
		adZurich.setPrice(9000);
		adZurich.setSquareFootage(32);
        adZurich.setNumberRooms(1.5f);
		adZurich.setProperty(APARTMENT);
		adZurich.setSmokers(false);
		adZurich.setAnimals(false);
		adZurich.setRoomDescription(roomDescription7);
		adZurich.setUser(oprah);
		adZurich.setTitle("Roommate wanted in Zürich");
		adZurich.setStreet("Hauptstrasse 61");
		adZurich.setCity("Zürich");
		adZurich.setGarden(false);
		adZurich.setBalcony(true);
		adZurich.setCellar(false);
		adZurich.setFurnished(true);
		adZurich.setGarage(true);
		date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(10));
		adZurich.setExpireDate(getTimedDate(1));
		pictures = new ArrayList<>();
		pictures.add(createPicture(adZurich, "/img/test/ad1_3.jpg"));
		pictures.add(createPicture(adZurich, "/img/test/ad1_2.jpg"));
		pictures.add(createPicture(adZurich, "/img/test/ad1_1.jpg"));
		adZurich.setPictures(pictures);
		adDao.save(adZurich);
	
		
		String studioDescription8 = "It is small studio close to the"
				+ "university and the bahnhof. The lovely neighbourhood"
				+ "Langgasse makes it an easy place to feel comfortable."
				+ "The studio is close to a Migross, Denner and the Coop."
				+ "The studio is 60m2. It has it own Badroom and kitchen."
				+ "Nothing is shared. The studio is fully furnished. The"
				+ "studio is also provided with a balcony. So if you want to"
				+ "have a privat space this could totally be good place for you.";


		Ad adLuzern = new Ad();
		adLuzern.setZipcode(6000);
		adLuzern.setMoveInDate(moveInDate8);
		adLuzern.setCreationDate(creationDate2);
		adLuzern.setPrice(7000);
		adLuzern.setSquareFootage(60);
        adLuzern.setNumberRooms(2);
		adLuzern.setProperty(STUDIO);
		adLuzern.setSmokers(false);
		adLuzern.setAnimals(false);
		adLuzern.setRoomDescription(studioDescription8);
		adLuzern.setUser(oprah);
		adLuzern.setTitle("Elegant Studio in Lucerne");
		adLuzern.setStreet("Schwanenplatz 61");
		adLuzern.setCity("Luzern");
		adLuzern.setGarden(false);
		adLuzern.setBalcony(false);
		adLuzern.setCellar(false);
		adLuzern.setFurnished(false);
		adLuzern.setGarage(false);
		date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(10));
		adLuzern.setExpireDate(getTimedDate(10));
		pictures = new ArrayList<>();
		pictures.add(createPicture(adLuzern, "/img/test/ad2_3.jpg"));
		pictures.add(createPicture(adLuzern, "/img/test/ad2_2.jpg"));
		pictures.add(createPicture(adLuzern, "/img/test/ad2_1.jpg"));
		adLuzern.setPictures(pictures);
		adDao.save(adLuzern);

		String studioDescription9 = " In the center of Gundeli (5 Min. away from the"
				+ "station, close to Tellplatz) there is a lovely house, covered with"
				+ "savage wine stocks, without any neighbours but with a garden. The"
				+ "house has two storey, on the first floor your new room is waiting"
				+ "for you. The house is totally equipped with everything a household "
				+ ": washing machine, kitchen, batroom, W-Lan...if you don´t have any"
				+ "furniture, don´t worry, I am sure, we will find something around"
				+ "the house.";

		Ad adAarau = new Ad();
		adAarau.setZipcode(5000);
		adAarau.setMoveInDate(moveInDate3);
		adAarau.setCreationDate(creationDate8);
		adAarau.setPrice(8000);
		adAarau.setSquareFootage(26);
        adAarau.setNumberRooms(3);
		adAarau.setProperty(STUDIO);
		adAarau.setSmokers(true);
		adAarau.setAnimals(false);
		adAarau.setRoomDescription(studioDescription9);
		adAarau.setUser(oprah);
		adAarau.setTitle("Beautiful studio in Aarau");
		adAarau.setStreet("Bruderholzstrasse 32");
		adAarau.setCity("Aarau");
		adAarau.setGarden(false);
		adAarau.setBalcony(true);
		adAarau.setCellar(false);
		adAarau.setFurnished(true);
		adAarau.setGarage(false);
		date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(10));
		adAarau.setExpireDate(getTimedDate(2));
		pictures = new ArrayList<>();
		pictures.add(createPicture(adAarau, "/img/test/ad3_3.jpg"));
		pictures.add(createPicture(adAarau, "/img/test/ad3_2.jpg"));
		pictures.add(createPicture(adAarau, "/img/test/ad3_1.jpg"));
		pictures.add(createPicture(adAarau, "/img/test/ad2_2.jpg"));
		pictures.add(createPicture(adAarau, "/img/test/ad2_3.jpg"));
		
		adAarau.setPictures(pictures);
		adDao.save(adAarau);
		
		String studioDescription10 = "Flat with 5 rooms"
				+ "on the second floor. The bedroom is about 60 square meters"
				+ "with access to a nice balcony. In addition to the room, the"
				+ "flat has: a living room, a kitchen, a bathroom, a seperate WC,"
				+ "a storage in the basement, a balcony, a laundry room in the basement."
				+ "The bedroom is big and bright and has a nice parquet floor."
				+ "Possibility to keep some furnitures like the bed.";

		Ad adDavos = new Ad();
		adDavos.setZipcode(7260);
		adDavos.setMoveInDate(moveInDate2);
		adDavos.setCreationDate(creationDate4);
		adDavos.setPrice(11000);
		adDavos.setSquareFootage(74);
        adDavos.setNumberRooms(5);
		adDavos.setProperty(HOUSE);
		adDavos.setSmokers(true);
		adDavos.setAnimals(false);
		adDavos.setRoomDescription(studioDescription10);
		adDavos.setUser(oprah);
		adDavos.setTitle("Free room in Davos City");
		adDavos.setStreet("Kathrinerweg 5");
		adDavos.setCity("Davos");
		adDavos.setGarden(false);
		adDavos.setBalcony(true);
		adDavos.setCellar(true);
		adDavos.setFurnished(true);
		adDavos.setGarage(false);
		date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(10));
		adDavos.setExpireDate(getTimedDate(3));
		pictures = new ArrayList<>();
		pictures.add(createPicture(adDavos, "/img/test/ad4_3.png"));
		pictures.add(createPicture(adDavos, "/img/test/ad4_2.png"));
		pictures.add(createPicture(adDavos, "/img/test/ad4_1.png"));
		adDavos.setPictures(pictures);
		adDao.save(adDavos);

		String studioDescription11 = "Studio meublé au 3ème étage, comprenant"
				+ "une kitchenette entièrement équipée (frigo, plaques,"
				+ "four et hotte), une pièce à vivre donnant sur un balcon,"
				+ "une salle de bains avec wc. Cave, buanderie et site satellite"
				+ "à disposition.";

		Ad adLausanne = new Ad();
		adLausanne.setZipcode(1000);
		adLausanne.setMoveInDate(moveInDate5);
		adLausanne.setCreationDate(creationDate5);
		adLausanne.setPrice(3600);
		adLausanne.setSquareFootage(8);
        adLausanne.setNumberRooms(1.5f);
		adLausanne.setProperty(APARTMENT);
		adLausanne.setSmokers(true);
		adLausanne.setAnimals(false);
		adLausanne.setRoomDescription(studioDescription11);
		adLausanne.setUser(oprah);
		adLausanne.setTitle("Studio extrèmement bon marché à Lausanne");
		adLausanne.setStreet("Rue de l'Eglise 26");
		adLausanne.setCity("Lausanne");
		adLausanne.setGarden(true);
		adLausanne.setBalcony(false);
		adLausanne.setCellar(true);
		adLausanne.setFurnished(true);
		adLausanne.setGarage(false);
		date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(10));
		adLausanne.setExpireDate(getTimedDate(20));
		pictures = new ArrayList<>();
		pictures.add(createPicture(adLausanne, "/img/test/ad5_3.jpg"));
		pictures.add(createPicture(adLausanne, "/img/test/ad5_2.jpg"));
		pictures.add(createPicture(adLausanne, "/img/test/ad5_1.jpg"));
		adLausanne.setPictures(pictures);
		adDao.save(adLausanne);

		String studioDescription12 = "A place just for yourself in a very nice part of Biel."
				+ "A studio for 1-2 persons with a big balcony, bathroom, kitchen and furniture already there."
				+ "It's quiet and nice, very close to the old city of Biel.";

		Ad adLocarno = new Ad();
		adLocarno.setZipcode(6600);
		adLocarno.setMoveInDate(moveInDate6);
		adLocarno.setCreationDate(creationDate6);
		adLocarno.setPrice(9600);
		adLocarno.setSquareFootage(42);
        adLocarno.setNumberRooms(2.5f);
		adLocarno.setProperty(HOUSE);
		adLocarno.setSmokers(true);
		adLocarno.setAnimals(false);
		adLocarno.setRoomDescription(studioDescription12);
		adLocarno.setUser(jane);
		adLocarno.setTitle("Malibu-style Beachhouse");
		adLocarno.setStreet("Kirchweg 12");
		adLocarno.setCity("Locarno");
		adLocarno.setGarden(false);
		adLocarno.setBalcony(false);
		adLocarno.setCellar(false);
		adLocarno.setFurnished(false);
		adLocarno.setGarage(false);
		date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(10));
		adLocarno.setExpireDate(getTimedDate(10));
		pictures = new ArrayList<>();
		pictures.add(createPicture(adLocarno, "/img/test/ad6_3.png"));
		pictures.add(createPicture(adLocarno, "/img/test/ad6_2.png"));
		pictures.add(createPicture(adLocarno, "/img/test/ad6_1.png"));
		adLocarno.setPictures(pictures);
		adDao.save(adLocarno);

	}

	private AdPicture createPicture(Ad ad, String filePath) {
		AdPicture picture = new AdPicture();
		picture.setFilePath(filePath);
		return picture;
	}

    private Date getTimedDate(int minutesToAdd) {
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        return new Date(t + (minutesToAdd * 60*1000));
    }

}