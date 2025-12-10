import React from "react";
import { Link } from "react-router-dom";

const CAR_CARD = ({ car }) => {
  return (
    <div className="col-12 col-md-6 col-lg-4">
      <div className="card shadow-sm h-100">

        
        <img
          src={car.images[0]}
          alt={car.make}
          className="card-img-top"
          style={{ height: "225px", objectFit: "cover" }}
        />

        <div className="card-body d-flex flex-column">

          <h5>
            {car.make} {car.model}
          </h5>

          <p className="card-text flex-grow-1 text-muted">
            {car.year} • ${car.price.toLocaleString()}
          </p>

          
          <Link to={`/car/${car.id}`} className="btn btn-primary w-100 mt-3">
            View
          </Link>

        </div>
      </div>
    </div>
  );
};

export default CAR_CARD;
