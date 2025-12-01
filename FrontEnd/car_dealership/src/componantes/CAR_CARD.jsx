import React from 'react';
import { Link } from 'react-router-dom';
import NAVBAR from './NAVBAR';

const CAR_CARD = () => {
  // Sample data for mapping
  const cars = [1, 2, 3, 4, 5];

  return (
    <>
      <div className="m-0 p-0">
        <NAVBAR />
      </div>

      <div className="container mt-5">
        <div className="row g-4">
          {cars.map((carId) => (
            <div key={carId} className="col-12 col-md-6 col-lg-4">
              <div className="card shadow-sm h-100">

                <svg
                  className="bd-placeholder-img card-img-top"
                  width="100%"
                  height="225"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <rect width="100%" height="100%" fill="#55595c"></rect>
                  <text
                    x="50%" y="50%" fill="#eceeef" dy=".3em" textAnchor="middle"
                  >
                    Thumbnail
                  </text>
                </svg>

                <div className="card-body d-flex flex-column">
                  <p className="card-text flex-grow-1">
                    This is a wider card with supporting text below.
                  </p>

                  {/* View button linking to a frontend page */}
                  <Link 
                    to={`/car/${carId}`} 
                    className="btn btn-primary w-100 mt-3"
                  >
                    View
                  </Link>

                </div>

              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default CAR_CARD;
