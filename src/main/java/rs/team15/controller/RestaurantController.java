package rs.team15.controller;

import java.awt.Menu;
//import java.sql.Date;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.team15.model.Bid;
import rs.team15.model.Bill;
import rs.team15.model.ClientOrder;
import rs.team15.model.Employee;
import rs.team15.model.Guest;
import rs.team15.model.MenuItem;
import rs.team15.model.Offer;
import rs.team15.model.OrderItem;
import rs.team15.model.Region;
import rs.team15.model.Reservation;
import rs.team15.model.Restaurant;

import rs.team15.model.RestaurantManager;
import rs.team15.model.Shift;
import rs.team15.model.Suplier;
import rs.team15.model.SystemManager;

import rs.team15.model.TableR;
import rs.team15.model.User;
import rs.team15.model.Waiter;
import rs.team15.model.Wanted;
import rs.team15.repository.TableRepository;
import rs.team15.repository.EmployeeRepository;
import rs.team15.repository.RestaurantRepository;
import rs.team15.service.BidService;
import rs.team15.service.EmployeeService;
import rs.team15.service.MenuItemService;
import rs.team15.service.OfferService;
import rs.team15.service.OrderService;
import rs.team15.service.RegionService;
import rs.team15.service.ReservationService;
import rs.team15.service.RestaurantService;
import rs.team15.service.ShiftService;
import rs.team15.service.SystemManagerService;
import rs.team15.service.TableService;
import rs.team15.service.UserService;
import rs.team15.service.WantedService;

@RestController
public class RestaurantController {


	private Logger logger = LoggerFactory.getLogger(this.getClass());
 	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SystemManagerService systemManagerService; 
	
	@Autowired
	private MenuItemService menuItemService;
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private ReservationService reservationService;

  @Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	private BidService bidService;
	
	@Autowired
	private WantedService wantedService;
	
	@Autowired
	private OfferService offerService;

  

	
	@RequestMapping(
            value    = "/api/restaurants",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Collection<Restaurant>> getRests() {
		logger.info("> get rest");
		Collection<Restaurant> rest = restaurantService.findAll();
		System.out.println(rest.size());
		for(Restaurant r :rest){
			System.out.println(r.getName());
			logger.info(r.getName());
		}
		logger.info("< get rest");
		return new ResponseEntity<Collection<Restaurant>>(rest, HttpStatus.OK);
	}
	
	//create offer
		@RequestMapping(value = "/api/offer/{bid:.+}/{email:.+}",
	            method = RequestMethod.POST,
	            consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity <Offer> CreateOffer(@RequestBody Offer offer,@PathVariable String bid,@PathVariable String email) {
			Suplier u = (Suplier) userService.findByEmail(email);
			offer.setSuplier(u);
			Bid b = bidService.findByName(bid);
			offer.setBid(b);
			offer.setStatus("pending");
			Offer created = offerService.create(offer);
			logger.info("< create offer");
			return new ResponseEntity<Offer>(created, HttpStatus.OK);
		}
		
		//update offer
		@RequestMapping(value = "/api/offer/update/{bid:.+}/{email:.+}",
			    method = RequestMethod.PUT,
			    consumes = MediaType.APPLICATION_JSON_VALUE,
			    produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity <Offer> EditOffer(@RequestBody Offer offer,@PathVariable String bid,@PathVariable String email) {
			Suplier u = (Suplier) userService.findByEmail(email);
			offer.setSuplier(u);
			Bid b = bidService.findByName(bid);
			offer.setBid(b);
			offer.setStatus("pending");
			Offer created = offerService.create(offer);
			logger.info("< create offer");
			return new ResponseEntity<Offer>(created, HttpStatus.OK);
		}
		
		//update offer
				@RequestMapping(value = "/api/offer/acc/{bid:.+}",
					    method = RequestMethod.PUT,
					    consumes = MediaType.APPLICATION_JSON_VALUE,
					    produces = MediaType.APPLICATION_JSON_VALUE)
				public ResponseEntity <Offer> EditOffer(@RequestBody Offer offer,@PathVariable String bid) {
					//Suplier u = (Suplier) userService.findByEmail(email);
					//offer.setSuplier(u);
					Bid b = bidService.findByName(bid);
					offer.setBid(b);
					offer.setStatus("accepted");
					Offer created = offerService.create(offer);
					logger.info("< create offer");
					return new ResponseEntity<Offer>(created, HttpStatus.OK);
				}
		
		
		
		//get offers
		@RequestMapping(
	            value    = "/api/alloffers/{name:.+}",
	            method   = RequestMethod.GET,
	            produces = MediaType.APPLICATION_JSON_VALUE
				)
		public ResponseEntity<List<Offer>> getOffers(@PathVariable String name) {
			logger.info("> get bid");
			Bid b = bidService.findByName(name);
			List<Offer> w = offerService.findAllByB(b);
			logger.info("aaaaaaaaaaaaaaaaaaaaa"+w.size());
			return new ResponseEntity<List<Offer>>(w, HttpStatus.OK);
		}
		
		@RequestMapping(
	            value    = "/api/myoffers/{email:.+}",
	            method   = RequestMethod.GET,
	            produces = MediaType.APPLICATION_JSON_VALUE
				)
		public ResponseEntity<List<Offer>> getMyOffers(@PathVariable String email) {
			logger.info("> get bid");
			Suplier s = (Suplier) userService.findByEmail(email);
			List<Offer> w = offerService.findBySuplier(s);
			logger.info("aaaaaaaaaaaaaaaaaaaaa"+w.size());
			return new ResponseEntity<List<Offer>>(w, HttpStatus.OK);
		}
		
		//accept offer
		@RequestMapping(value = "/api/offer/accept/{name}/{email:.+}",
	            method = RequestMethod.PUT,
	            consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity <Offer> AcceptOffer(@RequestBody List<Offer> offers,@PathVariable String name,@PathVariable String email) {
			for(Offer o: offers){
				//Suplier u = (Suplier) userService.findByEmail(email);
				//o.setSuplier(u);
				Bid b = bidService.findByName(name);
				o.setBid(b);
				o.setStatus("declined");
				Offer created = offerService.create(o);
			}
			Offer created = offers.get(0);
			return new ResponseEntity<Offer>(created, HttpStatus.OK);
		}
	
	//create wanted
		@RequestMapping(value = "/api/wanted/{id:.+}",
	            method = RequestMethod.POST,
	            consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity <Wanted> CreateWanted(@RequestBody Wanted wanted,@PathVariable String id) {
			Bid b = bidService.findByName(id);
			wanted.setBid(b);
	        Wanted created = wantedService.create(wanted);
			logger.info("< create wanted");
			return new ResponseEntity<Wanted>(created, HttpStatus.OK);
		}
		
		@RequestMapping(
	            value    = "/api/wanteds/{name:.+}",
	            method   = RequestMethod.GET,
	            produces = MediaType.APPLICATION_JSON_VALUE
				)
		public ResponseEntity<List<Wanted>> getWanted(@PathVariable String name) {
			logger.info("> get bid");
			Bid b = bidService.findByName(name);
			List<Wanted> w = wantedService.findAllByB(b);
			return new ResponseEntity<List<Wanted>>(w, HttpStatus.OK);
		}
	
	//create bid
	@RequestMapping(value = "/api/bid/{id}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Bid> CreateBid(@RequestBody Bid bid,@PathVariable String id) {
		Restaurant res = restaurantService.findById(id);
		bid.setRestaurant(res);
        Date curr = new Date();
        bid.setStarted("active");
        //bid.setStarted("closed");
        List<Offer> l = bid.getOffers();
        bid.setOffers(l);
        Bid created = bidService.create(bid);
		logger.info("< create bid");
		return new ResponseEntity<Bid>(created, HttpStatus.OK);
	}
	
	//create bid
		@RequestMapping(value = "/api/bid/deactivate",
	            method = RequestMethod.PUT,
	            consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity <Bid> DeactivateBid(@RequestBody Bid bid) {
			//Restaurant res = restaurantService.findById(id);
			//bid.setRestaurant(res);
	        //Date curr = new Date();
	        bid.setStarted("closed");
	        //bid.setStarted("closed");
	        //List<Offer> l = bid.getOffers();
	        //bid.setOffers(l);
	        Bid created = bidService.create(bid);
			logger.info("< create bid");
			return new ResponseEntity<Bid>(created, HttpStatus.OK);
		}
	
	@RequestMapping(
            value    = "/api/bids/{name}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<List<Bid>> getBids(@PathVariable String name) {
		logger.info("> get bid");
		Restaurant rest = restaurantService.findById(name);
		List<Bid> bid = bidService.findAllByRest(rest);
		System.out.println(bid.size());
		logger.info("< get bid " + bid.size());
		return new ResponseEntity<List<Bid>>(bid, HttpStatus.OK);
	}
	
	//get bids for suplier
	@RequestMapping(
            value    = "/api/bidssup/{email:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<List<Bid>> getBidsSup(@PathVariable String email) {
		logger.info("> get sbid");
		Suplier s = (Suplier) userService.findByEmail(email);
		logger.info("\nssasssssssssssssssss "+s.getRestaurants().size());
		List<Restaurant> rest = s.getRestaurants();
		System.out.println("rrrrrrrrrrrrrreeeeeees"+rest.size());
		List<Bid> bid = new ArrayList<Bid>();
		for(Restaurant r : rest){
			
			List<Bid> all = bidService.findAllByRest(r);
			for(Bid b : all){
				bid.add(b);
			}
		}
		System.out.println(bid.size());
		logger.info("< get sbid " + bid.size());
		return new ResponseEntity<List<Bid>>(bid, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/shifts",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<List<Shift>> getShifts() {
		logger.info("> get rest");
		List<Shift> rest = shiftService.findAll();
		System.out.println(rest.size());
		logger.info("< get rest");
		return new ResponseEntity<List<Shift>>(rest, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/restaurants/get/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<Restaurant> getRestById(@PathVariable String id) {
		logger.info("> get r id:{}", id);
		Restaurant r = restaurantService.findById(id);
	
		
		logger.info("< get r email:{}", id);
		return new ResponseEntity<Restaurant>(r, HttpStatus.OK);
	}
	@RequestMapping(
            value    = "/api/restaurants/hours/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<Collection<String>> getHours(@PathVariable String id) {
		logger.info("> get r id:{}", id);
		Restaurant r = restaurantService.findById(id);
	
		Collection<String> ret = new ArrayList<String>();
		for (int i = r.getStartTime(); i<=r.getEndTime();i++) {
			ret.add(i+":00");
			
		}
		logger.info("< get r email:{}", id);
		return new ResponseEntity<Collection<String>>(ret, HttpStatus.OK);
	}
	@RequestMapping(value = "/api/restaurants/{email:.+}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Restaurant> CreateRestaurant(@RequestBody Restaurant restaurant,@PathVariable("email") String email) {
		logger.info("< create userrrrrrrrrrrrrrrrrrrrr "+restaurant.getName()+"   "+email);
        SystemManager manager = (SystemManager)userService.findByEmail(email);
        restaurant.setSystemManager(manager);
		restaurant.setImage("pictures/user.png");
        restaurant.setMenuItemMenu(null);
        restaurant.setRegions(null);
        Restaurant created = restaurantService.create(restaurant);
		logger.info("< create user");
		return new ResponseEntity<Restaurant>(created, HttpStatus.OK);
	}
	
	//create shift
	@RequestMapping(value = "/api/shift/createShift/{email:.+}/{color}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Shift> CreateShift(@RequestBody Shift shift,@PathVariable String email,@PathVariable String color) {
		logger.info("< create shiiiiiiifffftr "+shift.isDraggable());
        User u = userService.findByEmail(email);
        Long id = u.getId();
        Employee em = employeeService.getEmployee(id);
        shift.setEmployee(em);
        shift.setColor(color);
        Shift created = shiftService.create(shift);
		return new ResponseEntity<Shift>(created, HttpStatus.OK);
	}
	
	//edit shift
		@RequestMapping(
	            value    = "api/shift/edit/{email:.+}/{color}",
	            method   = RequestMethod.PUT,
	            consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE
	    )
	    public ResponseEntity<Shift> editShift(@RequestBody Shift shift,@PathVariable String email,@PathVariable String color) {
			logger.info("> update shift");
			User u = userService.findByEmail(email);
	        Long id = u.getId();
	        Employee em = employeeService.getEmployee(id);
	        shift.setEmployee(em);
	        shift.setColor(color);
	        Shift updated = shiftService.create(shift);
	        logger.info("< update shift");
	        //logger.info("hello" + updated.getEmail() + " " + updated.getPassword());
	        return new ResponseEntity<Shift>(updated, HttpStatus.OK);
	    }
		
		//asign region
		@RequestMapping(
	            value    = "api/asignregion/{id}",
	            method   = RequestMethod.PUT,
	            consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE
	    )
	    public ResponseEntity<Employee> asignRegion(@RequestBody User user,@PathVariable Integer id) {
			logger.info("> asign region");
			Region r = regionService.findById(id);
			Employee employee = employeeService.getEmployee(user.getId());
	        employee.setRegion(r);
	        Employee updated = (Employee) employeeService.create(employee);
	        logger.info("< asign region");
	        //logger.info("hello" + updated.getEmail() + " " + updated.getPassword());
	        return new ResponseEntity<Employee>(updated, HttpStatus.OK);
	    }
		
		//delete shift
		@RequestMapping(
			            value    = "api/shift/delete/{email:.+}/{color}",
			            method   = RequestMethod.PUT,
			            consumes = MediaType.APPLICATION_JSON_VALUE,
			            produces = MediaType.APPLICATION_JSON_VALUE
		)
	    public ResponseEntity<Shift> deleteShift(@RequestBody Shift shift,@PathVariable String email,@PathVariable String color) {
					logger.info("> delete shift");
					User u = userService.findByEmail(email);
			        Long id = u.getId();
			        Employee em = employeeService.getEmployee(id);
			        shift.setEmployee(em);
			        shift.setColor(color);
			        shiftService.delete(shift);
			        logger.info("< delete shift");
			        //logger.info("hello" + updated.getEmail() + " " + updated.getPassword());
			        return new ResponseEntity<Shift>(HttpStatus.OK);
		}
	
	//add dishes
	@RequestMapping(value = "/api/dishes/{name}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <MenuItem> CreateDish(@RequestBody MenuItem menuItem,@PathVariable("name") String name) {
		logger.info("< create dishhhhhhhhhhhh "+name);
        Restaurant res = restaurantService.findById(name);
        menuItem.setRestaurant(res);
        menuItem.setImage("image");
        menuItem.setDeleted(false);
        menuItem.setType("dish");
        MenuItem created = menuItemService.create(menuItem);
        res.getMenuItemMenu().add(created);
		logger.info("< create user");
		return new ResponseEntity<MenuItem>(created, HttpStatus.OK);
	}
	
	//edit dishes
	@RequestMapping(
            value    = "api/dishes/edit",
            method   = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MenuItem> editDish(@RequestBody MenuItem dish) {
		logger.info("> update dish");
        MenuItem updated = menuItemService.create(dish);
        logger.info("< update dish");
        //logger.info("hello" + updated.getEmail() + " " + updated.getPassword());
        return new ResponseEntity<MenuItem>(updated, HttpStatus.OK);
    }
	
	//listDishes
	@RequestMapping(
            value    = "/api/dishes",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Collection<MenuItem>> getDishes() {
		logger.info("> get dishes");
		Collection<MenuItem> items = menuItemService.findAll();
		Collection<MenuItem> dishes = new ArrayList<MenuItem>();
		System.out.println(items.size());
		for(MenuItem r :items){
			System.out.println(r.getName());
			logger.info(r.getType());
			if(r.getType().equals("dish")){
				dishes.add(r);
			}
		}
		System.out.println(dishes.size());
		logger.info("< get dish");
		return new ResponseEntity<Collection<MenuItem>>(dishes, HttpStatus.OK);
	}
	
	//edit drink
		@RequestMapping(
	            value    = "api/drinks/edit",
	            method   = RequestMethod.PUT,
	            consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE
	    )
	    public ResponseEntity<MenuItem> editDrink(@RequestBody MenuItem drink) {
			logger.info("> update drink");
	        MenuItem updated = menuItemService.create(drink);
	        logger.info("< update drink");
	        //logger.info("hello" + updated.getEmail() + " " + updated.getPassword());
	        return new ResponseEntity<MenuItem>(updated, HttpStatus.OK);
	    }
	
	//add drinks
		@RequestMapping(value = "/api/drinks/{name}",
	            method = RequestMethod.POST,
	            consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity <MenuItem> CreateDrink(@RequestBody MenuItem menuItem,@PathVariable("name") String name) {
			logger.info("< create dishhhhhhhhhhhh "+name);
	        Restaurant res = restaurantService.findById(name);
	        menuItem.setRestaurant(res);
	        menuItem.setImage("image");
	        menuItem.setDeleted(false);
	        menuItem.setType("drink");
	        MenuItem created = menuItemService.create(menuItem);
	        res.getMenuItemMenu().add(created);
			logger.info("< create user");
			return new ResponseEntity<MenuItem>(created, HttpStatus.OK);
		}
		
		//listDishes
		@RequestMapping(
	            value    = "/api/drinks",
	            method   = RequestMethod.GET,
	            produces = MediaType.APPLICATION_JSON_VALUE
				)
		public ResponseEntity<Collection<MenuItem>> getDrinks() {
			logger.info("> get drinks");
			Collection<MenuItem> items = menuItemService.findAll();
			Collection<MenuItem> drinks = new ArrayList<MenuItem>();
			System.out.println(items.size());
			for(MenuItem r :items){
				System.out.println(r.getName());
				logger.info(r.getType());
				if(r.getType().equals("drink")){
					drinks.add(r);
				}
			}
			System.out.println(drinks.size());
			logger.info("< get drink");
			return new ResponseEntity<Collection<MenuItem>>(drinks, HttpStatus.OK);
		}
		
	//create new region
		@RequestMapping(value = "/api/restaurants/createRegion/{id}",
	            method = RequestMethod.POST,
	            consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity <Region> CreateRegion(@RequestBody Region region,@PathVariable("id") String id) {
			logger.info("< create regiooooooooon "+id);
	        Restaurant res = restaurantService.findById(id);
	        region.setRestaurant(res);
	        String color = region.getColor();
	        String newColor = color.substring(1);
	        region.setColor(newColor);
	        Region reg = regionService.findByRegno();
	        int num = reg.getRegionNo();
	        region.setRegionNo(num+1);
	        Region created = regionService.create(region);
			logger.info("< create user");
			return new ResponseEntity<Region>(created, HttpStatus.OK);
		}


	@RequestMapping(
            value    = "/api/restaurants/getAllATables/{datum:.+}/{vreme:.+}/{trajanje:.+}/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<Collection<TableR>> getAllATables(@PathVariable String datum,@PathVariable String vreme,@PathVariable String trajanje,@PathVariable String id) throws ParseException {
		logger.info("> get rrrrrrrrrrrrrrrrr id:{}", vreme);
		if(vreme.equals("undefined") || trajanje.equals("undefined") || datum.equals("undefined")){
			logger.info("nemaaa");
			Collection<TableR> ret = new ArrayList<TableR>();
			return new ResponseEntity<Collection<TableR>>(ret, HttpStatus.OK);
		}
		Restaurant r = restaurantService.findById(id);
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH);
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		java.util.Date date = format.parse(datum+" "+vreme);
		java.util.Date date2 = format.parse(datum+" "+trajanje);
		logger.info(date.toString());
		logger.info(date2.toString());
		if(date.after(date2)){
			Collection<TableR> rt = new ArrayList<TableR>();
			return new ResponseEntity<Collection<TableR>>(rt, HttpStatus.OK);
		}
		Date sad = new Date();
		if(sad.after(date)){
			Collection<TableR> rt = new ArrayList<TableR>();
			return new ResponseEntity<Collection<TableR>>(rt, HttpStatus.OK);
		}
		Collection<TableR> ret = new ArrayList<TableR>();
		Boolean available ;
		for (Iterator<Region> region = r.getRegions().iterator(); region.hasNext();) {
			System.out.println("regioooon ");
			Region sto = region.next();
			//for (Iterator<TableR> item = sto.getTables().iterator(); item.hasNext();) {
			//for (Iterator<TableR> item = tableService.findTablesByRegId(sto).iterator(); item.hasNext();) {
				System.out.println("stoooo ");
			for (Iterator<TableR> item = tableService.findTablesByRegId(sto).iterator(); item.hasNext();) {
			    TableR t = item.next();
			    available = true;
			    for (Iterator<Reservation> res = t.getReservations().iterator(); res.hasNext();) {
					System.out.println("reeez ");
				    Reservation reservation = res.next();
					java.util.Date dateOd = format.parse(reservation.getReservationDateTime()+" "+reservation.getTime());
					java.util.Date dateDo = format.parse(reservation.getReservationDateTime()+" "+reservation.getLength());
					dateOd.setMinutes(dateOd.getMinutes()-1);
					dateDo.setMinutes(dateDo.getMinutes()+1);
					logger.info(dateOd.toString()+"  mmmmmmmmmmmmmmmmmm "+reservation.getNameRest());
					logger.info(dateDo.toString());
					if(reservation.getStatus().equals("reserved")){
						if((date.after(dateOd) && date.before(dateDo))||(date2.after(dateOd) && date2.before(dateDo)) ){
					    	available = false;
						}
					}
				    
			    }
			    if(available){
				    ret.add(t);
			    }
			}
		}
		if(ret.size()==0){
			logger.info("nemaaa");
			Collection<TableR> rt = new ArrayList<TableR>();
			return new ResponseEntity<Collection<TableR>>(rt, HttpStatus.OK);
		}
		logger.info("< get r email:{}", r.getName());
		return new ResponseEntity<Collection<TableR>>(ret, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/restaurants/getAllTables/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<Collection<TableR>> getAllTables(@PathVariable String id) {
		logger.info("> get r id:{}", id);
		Restaurant r = restaurantService.findById(id);
	
		Collection<TableR> ret = new ArrayList<TableR>();

		for (Iterator<Region> region = r.getRegions().iterator(); region.hasNext();) {
			Region sto = region.next();
			//List<TableR> tables = tableService.findTablesByReg(sto.getRegId());
			//logger.info("aaaaaaaaaaaaaa" + tables.get(0));
			for (Iterator<TableR> item = tableService.findTablesByRegId(sto).iterator(); item.hasNext();) {
			    TableR t = item.next();
			    System.out.println(t.getTableId());
			    ret.add(t);
			}
		}
		logger.info("< get r email:{}", r.getName());
		return new ResponseEntity<Collection<TableR>>(ret, HttpStatus.OK);
	}
	@RequestMapping(
            value    = "/api/restaurants/find/{name:.+}/{type:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<Collection<Restaurant>> getByNameOrType(@PathVariable String name,@PathVariable String type) {
		logger.info("> get r name:{}", name);
		if(name.equals("a") && type.equals("a")){
			Collection<Restaurant> r = restaurantService.findAll();
			return new ResponseEntity<Collection<Restaurant>>(r, HttpStatus.OK);
		}
		Collection<Restaurant> r = restaurantService.findByNameOrType(name, type);
	
		logger.info("< get r name:{}", name);
		return new ResponseEntity<Collection<Restaurant>>(r, HttpStatus.OK);

	}
	
	/*@RequestMapping(
            value    = "/api/restaurants/{name:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<Restaurant> getRestByName(@PathVariable String name) {
		
		Restaurant r = restaurantService.findById(name);
		logger.info("< get name:{}", name);
		return new ResponseEntity<Restaurant>(r, HttpStatus.OK);
	}*/
	
	@RequestMapping(
            value    = "/api/getregions/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<Collection<Region>> getRegionsOfRestaurant(@PathVariable String id) {
		
		Restaurant r = restaurantService.findById(id);
		
		Collection<Region> regs = new ArrayList<Region>();
		for (Iterator<Region> region = r.getRegions().iterator(); region.hasNext();) {
			Region reg = region.next();
			System.out.println(reg.getRegionNo());
			regs.add(reg);
		}
		return new ResponseEntity<Collection<Region>>(regs, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "api/users/createTable",
            method   = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<TableR> createTable(@RequestBody TableR table) {
		table.setDeleted("no");
		
	       
		TableR created = restaurantService.create(table);
		//logger.info("< create table {}", table.getRegion().getRegId());
		return new ResponseEntity<TableR>(created, HttpStatus.CREATED);
	}
	
	@RequestMapping(
            value    = "api/users/updateTable",
            method   = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<TableR> updateTable(@RequestBody TableR table) {
		/*table.setWidth(50.00);
		table.setHeight(50.00);
		table.setDatax(400.00);
		table.setDatay(400.00);*/
	       
		TableR updated = restaurantService.update(table);
		logger.info("< create table");
		return new ResponseEntity<TableR>(updated, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "api/users/deleteTable",
            method   = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<TableR> deleteTable(@RequestBody TableR table) {
		TableR deleted = restaurantService.delete(table);
		logger.info("< delete table {}", table.getTableInRestaurantNo());
		
		return new ResponseEntity<TableR>(deleted, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/users/findTable/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<TableR> findTableById(@PathVariable Integer id) {
		
		TableR t = restaurantService.find(id);

		return new ResponseEntity<TableR>(t, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "api/users/updateTable2",
            method   = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<TableR> updateTable2(@RequestBody TableR table) {
		/*table.setWidth(50.00);
		table.setHeight(50.00);
		table.setDatax(400.00);
		table.setDatay(400.00);*/
	       
		TableR updated = restaurantService.update2(table);
		logger.info("< update table");
		return new ResponseEntity<TableR>(updated, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "api/orders/addOne/{id:.+}",
            method   = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<OrderItem> addOneItem(@RequestBody OrderItem item, @PathVariable Integer id) {
		ClientOrder o = orderService.find(id);
		logger.info("< got order {} ", o.getOrderNumber());
		item.setPrice(item.getMenuItem().getPrice() * item.getAmount());
		item.setOrder(o);
		item.setState("waiting");
		//order.getOrderItems().add(item);
		//order.setTotalPrice(order.getTotalPrice() + item.getPrice());
		OrderItem created = orderService.addNew(item);
		
		//orderService.update(order);
		logger.info("< create item {}", item.getOiid());
		return new ResponseEntity<OrderItem>(created, HttpStatus.CREATED);
	}
	
	@RequestMapping(
            value    = "api/orders/addOrder",
            method   = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<ClientOrder> addOrder(@RequestBody ClientOrder order) {
		//logger.info("< create order {}", items.size());
		//Order order = new Order();
		//order.setOrderItems(items);
		//double sum = 0;
		/*for(OrderItem i : items){
			sum += i.getPrice();
		}*/
		logger.info("< create order - rest: {}", order.getEmployee().getRestaurant().getName());
		Restaurant r = order.getEmployee().getRestaurant();
		order.setRestaurant(r);
		order.setTotalPrice(0);
		order.setStatus("waiting");  
		ClientOrder created = orderService.create(order);
		/*for (Iterator<OrderItem> i = items.iterator(); i.hasNext();) {
			OrderItem oi = i.next();
			oi.setOrder(order);
			OrderItem updated = orderService.update(oi);
		}*/
		//logger.info("< create table {}", table.getRegion().getRegId());
		return new ResponseEntity<ClientOrder>(created, HttpStatus.CREATED);
	}
	
	@RequestMapping(
            value    = "api/orders/saveOrder/{table:.+}",
            method   = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<ClientOrder> saveOrder(@RequestBody ClientOrder order, @PathVariable Integer table) {
		//ClientOrder o = orderService.find(id);
		TableR t = tableService.findByrno(table);
		logger.info("< table: {}", t.getTableInRestaurantNo());
		order.setTable(t);
		order.setStatus("waiting");
		ClientOrder created = orderService.create(order);
		Set<OrderItem> items = created.getItems();
		logger.info("< items num {}", items.size());
		double sum = 0;
		for(OrderItem i : items){
			i.setOrder(created);
			i.setState("waiting");
			i.setPrice(i.getMenuItem().getPrice() * i.getAmount());
			OrderItem oi = orderService.addNew(i);
			sum += i.getPrice();
		}
		created.setTotalPrice(sum);
		ClientOrder updated = orderService.update(created);
		
		
		return new ResponseEntity<ClientOrder>(updated, HttpStatus.CREATED);
	}
	
	@RequestMapping(
            value    = "/api/orders/getAll/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Collection<ClientOrder>> getAllOrders(@PathVariable String id) {
		Collection<ClientOrder> orders = orderService.findByEmployee(id);
		Collection<ClientOrder> forWaiters = new ArrayList<ClientOrder>();
		for(ClientOrder o : orders){
			if(o.getStatus().equals("waiting")){
				forWaiters.add(o);
			}
		}
		logger.info("< get orders");
		return new ResponseEntity<Collection<ClientOrder>>(forWaiters, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/orders/getAll2/{id:.+}/{type:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Collection<OrderItem>> getAllDishes(@PathVariable String id, @PathVariable String type) {
		Collection<ClientOrder> orders = orderService.findByRestaurant(id);
		Collection<OrderItem> dishes = new ArrayList<OrderItem>();
		for(ClientOrder o : orders){
			for(OrderItem i : o.getItems()){
				if(i.getMenuItem().getType().equals("dish") && i.getMenuItem().getSpecType().equals(type) && i.getState().equals("waiting")){
					logger.info("<added order: {}", o.getOrderNumber());
					dishes.add(i);
				}
			}

			
		}
		
		return new ResponseEntity<Collection<OrderItem>>(dishes, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/orders/getAll3/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Collection<OrderItem>> getAllDrinks(@PathVariable String id) {
		Collection<ClientOrder> orders = orderService.findByRestaurant(id);
		Collection<OrderItem> drinks = new ArrayList<OrderItem>();
		for(ClientOrder o : orders){
			for(OrderItem i : o.getItems()){
				if(i.getMenuItem().getType().equals("drink") && i.getState().equals("waiting")){
					logger.info("<added order: {}", o.getOrderNumber());
					drinks.add(i);
				}
			}

			
		}
		
		return new ResponseEntity<Collection<OrderItem>>(drinks, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/orders/take/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<OrderItem> take(@PathVariable Integer id) {
		OrderItem oi = orderService.take(id);
		/*OrderItem item = orderService.findItem(id);
		OrderItem r = item;
		item.setState("in progress");
		//item.getOrder().setStatus("in progress");
		logger.info("< take item: {}", item.getItemNumber());
		OrderItem updated = orderService.updateItem(item);
		logger.info("< taken: {}", updated.getMenuItem().getName());
		ClientOrder order = item.getOrder();
		order.setStatus("in progress");
		logger.info("< size before: {}", order.getItems().size());
		order.getItems().remove(r);
		logger.info("< size after: {}", order.getItems().size());
		order.getItems().add(updated);
		logger.info("< size afterrrrr: {}", order.getItems().size());
		ClientOrder up = orderService.update(order);*/
		if(oi == null){
			return new ResponseEntity<OrderItem>(oi, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<OrderItem>(oi, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/orders/getTaken/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Collection<OrderItem>> getTakenDishes(@PathVariable String id) {
		Collection<ClientOrder> orders = orderService.findByRestaurant(id);
		Collection<OrderItem> taken = new ArrayList<OrderItem>();
		for(ClientOrder o : orders){
			for(OrderItem i : o.getItems()){
				if(i.getMenuItem().getType().equals("dish") && i.getState().equals("in progress")){
					logger.info("<added order: {}", o.getOrderNumber());
					taken.add(i);
				}
			}	
		}
		return new ResponseEntity<Collection<OrderItem>>(taken, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/orders/getTaken2/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Collection<OrderItem>> getTakenDrinks(@PathVariable String id) {
		Collection<ClientOrder> orders = orderService.findByRestaurant(id);
		Collection<OrderItem> taken = new ArrayList<OrderItem>();
		for(ClientOrder o : orders){
			for(OrderItem i : o.getItems()){
				if(i.getMenuItem().getType().equals("drink") && i.getState().equals("in progress")){
					logger.info("<added order: {}", o.getOrderNumber());
					taken.add(i);
				}
			}	
		}
		return new ResponseEntity<Collection<OrderItem>>(taken, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/orders/finish/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<OrderItem> finish(@PathVariable Integer id) {
		OrderItem item = orderService.findItem(id);
		OrderItem r = item;
		item.setState("finished");
		logger.info("< finish item: {}", item.getItemNumber());
		OrderItem updated = orderService.updateItem(item);
		logger.info("< finished: {}", updated.getMenuItem().getName());
		ClientOrder order = item.getOrder();
		boolean check = false;
		for(OrderItem i : order.getItems()){
			if(!i.getState().equals("finished")){
				check = true;
				break;
			}
		}
		if(check == false){
			order.setStatus("finished");
			
			Reservation res = order.getReservation();
			if(res == null){
				
			}
			else {
				boolean check2 = false;
				TableR table = res.getTid();
				Collection<ClientOrder> co = orderService.findByReservationAndTable(res.getReservationId(), table.getTableId());
				logger.info("< num of client orders: {}", co.size());
				for(ClientOrder oo : co){
					if(!oo.getStatus().equals("finished")){
						check2 = true;
						break;
					}
				}
				if(check2 == false){
					res.setStatus("finished");
					User u = res.getUid();
					u.setBrojPoseta(u.getBrojPoseta() + 1);
					User update = userService.updateVisits(u);
					Reservation updateR = reservationService.update(res);
				}
			}
		}
		//order.setStatus("in progress");
		logger.info("< size before: {}", order.getItems().size());
		order.getItems().remove(r);
		logger.info("< size after: {}", order.getItems().size());
		order.getItems().add(updated);
		logger.info("< size afterrrrr: {}", order.getItems().size());
		ClientOrder up = orderService.update(order);
		return new ResponseEntity<OrderItem>(updated, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/orders/getFinished/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Collection<ClientOrder>> getFinishedOrders(@PathVariable String id) {
		Collection<ClientOrder> orders = orderService.findByEmployee(id);
		Collection<ClientOrder> forWaiters = new ArrayList<ClientOrder>();
		for(ClientOrder o : orders){
			if(o.getStatus().equals("finished")){
				forWaiters.add(o);
			}
		}
		logger.info("< get finished orders");
		return new ResponseEntity<Collection<ClientOrder>>(forWaiters, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/orders/getOrder/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<ClientOrder> getOrder(@PathVariable Integer id) {
		ClientOrder order = orderService.find(id);
		logger.info("< get order {}", order.getItems().size());
		return new ResponseEntity<ClientOrder>(order, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/orders/getMenuItems/{name:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Collection<MenuItem>> getMenuItems(@PathVariable String name) {
		Collection<MenuItem> items = menuItemService.findAll();
		Collection<MenuItem> items2 = new ArrayList<MenuItem>();
		for(MenuItem m : items){
			if(m.getRestaurant().getName().equals(name)){
				items2.add(m);
			}
		}
		logger.info("< get menu items {}", items2.size());
		return new ResponseEntity<Collection<MenuItem>>(items2, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "api/orders/deleteItem/{id:.+}",
            method   = RequestMethod.PUT,
            //consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<OrderItem> deleteItem(@PathVariable Integer id) {
		OrderItem oi = orderService.deleteI(id);
		/*OrderItem item = orderService.findItem(id);
		logger.info("< deleting item {}", item.getItemNumber());
		
		OrderItem deleted = orderService.delete(item);*/
		
		if(oi==null){
			return new ResponseEntity<OrderItem>(oi, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<OrderItem>(oi, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "api/orders/saveEditedOrder/{id:.+}",
            method   = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<ClientOrder> saveEdited(@PathVariable Integer id) {
		ClientOrder order = orderService.find(id);
		logger.info("< editing order num {}", order.getOrderNumber());
		Collection<OrderItem> items = order.getItems();
		double sum = 0;
		for(OrderItem i : items){
			sum += i.getPrice();
		}
		order.setTotalPrice(sum);
		logger.info("< total sum {}", order.getTotalPrice());
		
		ClientOrder created = orderService.update(order);
		
		return new ResponseEntity<ClientOrder>(created, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "api/orders/saveEditedItem/{id:.+}/{amount:.+}",
            method   = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<OrderItem> saveEditedItem(@PathVariable Integer id, @PathVariable Integer amount) {
		OrderItem item = orderService.findItem(id);
		item.setAmount(amount);
		item.setPrice(item.getMenuItem().getPrice() * item.getAmount());
		logger.info("< editing item num {}", item.getItemNumber());
		
		//logger.info("< total sum {}", order.getTotalPrice());
		
		OrderItem created = orderService.updateItem(item);
		
		return new ResponseEntity<OrderItem>(created, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "api/orders/makeBill/{id:.+}/{email:.+}",
            method   = RequestMethod.GET,
            //consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<Bill> makeBill(@PathVariable Integer id, @PathVariable String email) {
		Waiter w = (Waiter) userService.findByEmail(email);
		logger.info("< create bill: {}", w.getEmail());
		ClientOrder order = orderService.find(id);
		Bill bill = new Bill();
		bill.setDate(new Date());
		logger.info("< create bill: {}", bill.getDate());
		bill.setWaiter(w);
		order.setStatus("done");
		ClientOrder o = orderService.update(order);
		bill.setOrder(o);
		bill.setTotalPrice(order.getTotalPrice());
		logger.info("< create bill: {}", bill.getTotalPrice());
		logger.info("< create bill: {} {}", bill.getWaiter().getFirstName());
		Bill created = orderService.createBill(bill);
		return new ResponseEntity<Bill>(created, HttpStatus.CREATED);
	}
	
	@RequestMapping(
            value    = "/api/orders/getTables/{id:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Collection<TableR>> getTables(@PathVariable String id) throws ParseException {
		Employee e = (Employee) userService.findByEmail(id);
		logger.info("<employee: {}", e.getEmail());
		logger.info("<employee reg: {}", e.getRegion().getName());
		Collection<TableR> tablesOfReg = tableService.findTablesByRegId(e.getRegion());
		logger.info("< num of tables: {}", tablesOfReg.size() );
		Collection<TableR> availables = new ArrayList<TableR>();
		Collection<Reservation> ress = reservationService.findByStatus("reserved");
		Collection<ClientOrder> orders = orderService.findAll();
		Collection<TableR> taken = new ArrayList<TableR>();
		logger.info("> size {}", orders.size());
		Collection<ClientOrder> oo = new ArrayList<ClientOrder>();
		for(ClientOrder co : orders){
			if(!co.getStatus().equals("done")){
				logger.info("dodajem");
				oo.add(co);
			}
		}
		Date d = new Date();
		logger.info("< {}", d);
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH);
		
		boolean available;
		for (TableR t : tablesOfReg) {
		    available = true;
		    for (Reservation r : ress) {
		    	String s1 = r.getReservationDateTime() + " " + r.getTime();
				String s2 = r.getReservationDateTime() + " " + r.getLength();
				Date date1 = format.parse(s1);
				logger.info("< od: {}", date1);
				Date date2 = format.parse(s2);
				logger.info("< do: {}", date2);
				if((d.after(date1) && d.before(date2)) && r.getTid().equals(t)){
					logger.info("zauzet");
				    available = false;
				    taken.add(t);
					}
				}
		    /*if(available){
			    availables.add(t);
		    }*/
		}
		
		boolean available2;
		for (TableR t : tablesOfReg) {
		    available2 = true;
		    for (ClientOrder c : oo) {
		    	if(c.getTable() != null) {
		    		
					if(c.getTable().equals(t)){
						logger.info("zauzet");
					    available2 = false;
					    taken.add(t);
						}
				}
		    }
		    /*if(available2 && !availables.contains(t)){
		    	logger.info("dodajjjjj");
			    availables.add(t);
		    }*/
		}
		
		for(TableR t : tablesOfReg){
			if(!taken.contains(t)){
				availables.add(t);
			}
		}
		
		logger.info("> number of availables: {}", availables.size());
		
		return new ResponseEntity<Collection<TableR>>(availables, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "/api/orders/getPending/{email:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Collection<ClientOrder>> getPending(@PathVariable String email) {
		Employee e = (Employee) userService.findByEmail(email);
		logger.info("< get waiter {}", e.getEmail());
		Restaurant r = e.getRestaurant();
		logger.info("< get rest {}", e.getRestaurant().getName());
		Region reg = e.getRegion();
		logger.info("< get region {}", e.getRegion().getName());
		Collection<ClientOrder> co = orderService.findByStatusAndRestaurant("created", r);
		logger.info("< client orders size {}", co.size());
		Collection<ClientOrder> orders = new ArrayList<ClientOrder>();
		for(ClientOrder c : co){
			if(c.getReservation().getTid().getRegion().equals(reg)){
				orders.add(c);
			}
		}
		logger.info("< get orders {}", orders.size());
		return new ResponseEntity<Collection<ClientOrder>>(orders, HttpStatus.OK);
	}
	
	@RequestMapping(
            value    = "api/orders/takeOrder/{email:.+}/{id:.+}",
            method   = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<ClientOrder> takeOrder(@PathVariable String email, @PathVariable Integer id) {
		Employee e = (Employee) userService.findByEmail(email);
		logger.info("< empl {}", e.getFirstName());
		ClientOrder co = orderService.find(id);
		co.setEmployee(e);
		co.setStatus("waiting");
		double sum = 0;
		for(OrderItem oi : co.getItems()){
			double p = oi.getMenuItem().getPrice() * oi.getAmount();
			oi.setPrice(p);
			oi.setState("waiting");
			OrderItem item = orderService.updateItem(oi);
			sum += p;
		}
		co.setTotalPrice(sum);
		ClientOrder created = orderService.update(co);		
		return new ResponseEntity<ClientOrder>(created, HttpStatus.OK);
	}
}
