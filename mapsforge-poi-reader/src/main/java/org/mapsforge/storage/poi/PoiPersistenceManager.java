/*
 * Copyright 2010, 2011 mapsforge.org
 * Copyright 2010, 2011 weise
 * Copyright 2010, 2011 Karsten Groll
 * Copyright 2015 devemux86
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.mapsforge.storage.poi;

import org.mapsforge.core.GeoCoordinate;

import java.util.Collection;

/**
 * Abstracts from an underlying Storage/DB by providing methods for inserting / deleting / searching
 * {@link PointOfInterest} objects in named Storage/DB.
 * <p/>
 * Remember to call the {@link #close()} method as soon as you are done manipulating the Storage/DB
 * via this {@link PoiPersistenceManager}.
 */
public interface PoiPersistenceManager {
	/**
	 * Use this to free claimed resources. After that you might no longer be able to query for
	 * points of interest. This should always be called a soon as you are done querying.
	 */
	void close();

	/**
	 * Find all {@link PointOfInterest} in a rectangle specified by the two given
	 * {@link GeoCoordinate}s.
	 *
	 * @param p1
	 *            {@link GeoCoordinate} specifying one corner of the rectangle. (minLat, minLon)
	 * @param p2
	 *            {@link GeoCoordinate} specifying one corner of the rectangle. (maxLat, maxLon)
	 * @param limit
	 *            max number of {@link PointOfInterest} to be returned.
	 * @return {@link Collection} of {@link PointOfInterest} contained in the rectangle specified by
	 *         the two given {@link GeoCoordinate}s.
	 */
	Collection<PointOfInterest> findInRect(GeoCoordinate p1, GeoCoordinate p2, int limit);

	/**
	 * Find all {@link PointOfInterest} of the given {@link PoiCategory} in a rectangle specified by
	 * the two given {@link GeoCoordinate}s.
	 *
	 * @param p1
	 *            {@link GeoCoordinate} specifying one corner of the rectangle. (minLat, minLon)
	 * @param p2
	 *            {@link GeoCoordinate} specifying one corner of the rectangle. (maxLat, maxLon)
	 * @param categoryName
	 *            unique title of {@link PoiCategory} the returned {@link PointOfInterest} should
	 *            belong to.
	 * @param limit
	 *            max number of {@link PointOfInterest} to be returned.
	 * @return {@link Collection} of {@link PointOfInterest} of the given {@link PoiCategory}
	 *         contained in the rectangle specified by the two given {@link GeoCoordinate}s.
	 */
	Collection<PointOfInterest> findInRect(GeoCoordinate p1, GeoCoordinate p2, String categoryName,
										   int limit);

	/**
	 * Find all {@link PointOfInterest} in a rectangle specified by the two given
	 * {@link GeoCoordinate}s. Only the POIs that are allowed by the {@link PoiCategoryFilter}
	 * object will be returned.
	 *
	 * @param p1
	 *            {@link GeoCoordinate} specifying one corner of the rectangle. (minLat, minLon)
	 * @param p2
	 *            {@link GeoCoordinate} specifying one corner of the rectangle. (maxLat, maxLon)
	 * @param categoryFilter
	 *            POI category filter object that helps determining whether a POI should be added to
	 *            the set or not.
	 * @param limit
	 *            max number of {@link PointOfInterest} to be returned.
	 * @return {@link Collection} of {@link PointOfInterest} matching a given
	 *         {@link PoiCategoryFilter} contained in the rectangle specified by the two given
	 *         {@link GeoCoordinate}s.
	 */
	Collection<PointOfInterest> findInRectWithFilter(GeoCoordinate p1, GeoCoordinate p2,
													 PoiCategoryFilter categoryFilter, int limit);

	/**
	 * Fetch {@link PointOfInterest} from underlying storage near a given position.
	 *
	 * @param point
	 *            {@link GeoCoordinate} center of the search.
	 * @param distance
	 *            in meters
	 * @param limit
	 *            max number of {@link PointOfInterest} to be returned.
	 * @return {@link Collection} of {@link PointOfInterest} near the given position.
	 */
	Collection<PointOfInterest> findNearPosition(GeoCoordinate point, int distance, int limit);

	/**
	 * Fetch {@link PointOfInterest} of the given {@link PoiCategory} from underlying storage near a
	 * given position.
	 *
	 * @param point
	 *            {@link GeoCoordinate} center of the search.
	 * @param distance
	 *            in meters
	 * @param categoryName
	 *            unique title of {@link PoiCategory} the returned {@link PointOfInterest} should
	 *            belong to.
	 * @param limit
	 *            max number of {@link PointOfInterest} to be returned.
	 * @return {@link Collection} of {@link PointOfInterest} of the given {@link PoiCategory} near
	 *         the given position.
	 */
	Collection<PointOfInterest> findNearPosition(GeoCoordinate point, int distance,
												 String categoryName, int limit);

	/**
	 * Fetch {@link PointOfInterest} from underlying storage near a given position. Only the POIs
	 * that are allowed by the {@link PoiCategoryFilter} object will be returned.
	 *
	 * @param point
	 *            {@link GeoCoordinate} center of the search.
	 * @param distance
	 *            in meters
	 * @param categoryFilter
	 *            POI category filter object that helps determining whether a POI should be added to
	 *            the set or not.
	 * @param limit
	 *            max number of {@link PointOfInterest} to be returned.
	 * @return {@link Collection} of {@link PointOfInterest} matching a given
	 *         {@link PoiCategoryFilter} near the given position.
	 */
	Collection<PointOfInterest> findNearPositionWithFilter(GeoCoordinate point, int distance,
														   PoiCategoryFilter categoryFilter,
														   int limit);

	/**
	 * @param poiID
	 *            the id of the point of interest that shall be returned.
	 * @return a single {@link PointOfInterest} p where p.id == poiID.
	 */
	PointOfInterest findPointByID(long poiID);

	/**
	 * @return The persistence manager's category manager for retrieving and editing POI categories.
	 */
	PoiCategoryManager getCategoryManager();

	/**
	 * Inserts a single {@link PointOfInterest} into storage.
	 *
	 * @param poi
	 *            {@link PointOfInterest} to insert into storage.
	 */
	void insertPointOfInterest(PointOfInterest poi);

	/**
	 * Inserts {@link PointOfInterest} into storage.
	 *
	 * @param pois
	 *            collection of {@link PointOfInterest} to insert into storage.
	 */
	void insertPointsOfInterest(Collection<PointOfInterest> pois);

	/**
	 * Removes a point of interest from storage.
	 *
	 * @param poi
	 *            the {@link PointOfInterest} to be removed.
	 */
	void removePointOfInterest(PointOfInterest poi);

	/**
	 * Sets this manager's {@link PoiCategoryManager} for retrieving and editing POI categories.
	 *
	 * @param categoryManager
	 *            The category manager to be set.
	 */
	void setCategoryManager(PoiCategoryManager categoryManager);
}
