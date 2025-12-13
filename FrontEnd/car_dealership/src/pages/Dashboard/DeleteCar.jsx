import React, { useState } from "react";
import CarsTable from "../../componantes/CarsTable";

const DeleteCar = ({cars,setCars}) => {
  function deleteCar(car){
   setCars(prev=>prev.filter(c=>c.id!==car.id));
}
  
  return <div className="mt-5"> 
        <h2 className="text-center text-danger">Delete car</h2>

    <CarsTable cars={cars} deleteCar={deleteCar}/>
  </div>;
};

export default DeleteCar;
