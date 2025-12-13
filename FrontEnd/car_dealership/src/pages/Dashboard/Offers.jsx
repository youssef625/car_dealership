import { useState } from "react";
import OffersTable from "../../componantes/OffersTable";

const Offers = () => {
  const [offers, setOffers] = useState([
    {
      id: 1,
      cusName: "Ahmed Attia",
      car: "Toyota Corolla 2020",
      price: 15500,
      status: "available",
    },
    {
      id: 2,
      cusName: "Hassan Mohamed",
      car: "Honda Civic 2019",
      price: 14500,
      status: "reserved",
    },
    {
      id: 3,
      cusName: "Mostafa Ali",
      car: "Ford Mustang 2021",
      price: 31000,
      status: "canceled",
    },
    {
      id: 4,
      cusName: "Yousef Nader",
      car: "Toyota Corolla 2020",
      price: 15000,
      status: "available",
    },
    {
      id: 5,
      cusName: "Abdallah Mahmoud",
      car: "Honda Civic 2019",
      price: 14200,
      status: "booked",
    },
  ]);

  return (
    <div className="mt-5">
      <h2 className="text-center text-danger">Offers</h2>
      <OffersTable offers={offers} setOffers={setOffers}/>
    </div>
  );
};

export default Offers;
