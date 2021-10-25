package jcperezz.codigoton;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class Filter.
 */
public class Filter {

	/**
	 * Instantiates a new filter.
	 */
	private Filter() {
		super();
	}

	/**
	 * Search.
	 *
	 * @param resultList the result list
	 * @param filters the filters
	 * @return the list
	 */
	public static List<Map<String, String>> search(List<Map<String, String>> resultList, Map<String, String> filters) {

		List<Map<String, String>> result = new LinkedList<>();

		for (Map<String, String> target : resultList) {

			Iterator<Entry<String, String>> it = filters.entrySet().iterator();
			boolean isValid = Boolean.TRUE;

			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();

				if (isValid && "TC".equals(entry.getKey())) {
					isValid = target.get("type").equals(entry.getValue());

				} else if (isValid && "UG".equals(entry.getKey())) {
					isValid = target.get("location").equals(entry.getValue());
				} else if (isValid && "RI".equals(entry.getKey())) {
					Double value = Double.valueOf(entry.getValue());
					Double balance = Double.valueOf(target.get("total_balance"));

					isValid = balance.compareTo(value) > 0;

				} else if (isValid && "RF".equals(entry.getKey())) {
					Double value = Double.valueOf(entry.getValue());
					Double balance = Double.valueOf(target.get("total_balance"));

					isValid = balance.compareTo(value) < 0;
				} else {
					isValid = Boolean.FALSE;
				}
			}

			if (isValid) {
				result.add(target);
			}
		}

		return otro(result);

	}

	/** The Constant TOTAL. */
	private static final int TOTAL = 8;
	
	/** The Constant TOTAL_MALE. */
	private static final int TOTAL_MALE = 4;

	/**
	 * Otro.
	 *
	 * @param resultList the result list
	 * @return the list
	 */
	public static List<Map<String, String>> otro(List<Map<String, String>> resultList) {
		int maleCount = 0;
		int femaleCount = 0;

		Set<String> companies = new HashSet<>();

		List<Map<String, String>> result = new LinkedList<>();

		for (Map<String, String> map : resultList) {

			if (result.size() < TOTAL) {

				String companyClient = map.get("company");
				boolean isMale = map.get("male").equals("1");

				if (!companies.contains(companyClient)) {

					if (isMale && maleCount < TOTAL_MALE) {
						maleCount++;
						result.add(map);
						companies.add(companyClient);
					} else if (!isMale && femaleCount < TOTAL_MALE) {
						femaleCount++;
						result.add(map);
						companies.add(companyClient);
					}
				}

			} else {
				break;
			}

		}

		while (maleCount != femaleCount) {
			String isMale = maleCount > femaleCount ? "1" : "0";
			for (int i = result.size() - 1; i >= 0; i--) {
				if (result.get(i).get("male").equals(isMale)) {
					result.remove(i);

					if (isMale.equals("1")) {
						maleCount--;
					} else {
						femaleCount--;
					}
					
					break;
				}
			}

		}

		return result;

	}

}
