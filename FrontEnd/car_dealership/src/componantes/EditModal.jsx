import { useState } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
const EditModal = ({ cars, car, setCars }) => {
  async function handleSubmit(e) {
    const updatedCarData = cars.find((c) => c.id === car.id);
    e.preventDefault();
    if (
      !car.make ||
      !car.model ||
      !car.year ||
      !car.price ||
      !car.description
    ) {
      alert("Please fill all fields");
      return;
    }
    const token = localStorage.getItem("token");
    console.log(import.meta.env.VITE_BASE_URL);

    try {
      const res = await fetch(
        `${import.meta.env.VITE_FINAL_BASE_URL}/api/cars/${car.id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(updatedCarData),
        }
      );

      if (!res.ok) {
        throw new Error("Failed to update car");
      }

      const updatedCar = await res.json();
      console.log("Updated:", updatedCar);
      alert("Car updated successfully");
      handleClose();
    } catch (error) {
      console.error("Update error:", error);
      alert("Error updating car");
    }
  }
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  function handleInput(carp, input, value) {
    let newArr = [];
    newArr = cars.map((car) => {
      if (car.id == carp.id) {
        return { ...car, [input]: value };
      }
      return car;
    });
    setCars(newArr);
  }
  return (
    <>
      <Button onClick={handleShow} variant="secondary">
        Edit
      </Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Edit Car</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <h3>{"ID:" + " " + car.id}</h3>
            <Form.Group className="mb-3">
              <Form.Label>Country</Form.Label>
              <Form.Control
                name="make"
                type="text"
                value={car.make}
                placeholder="Enter Country"
                onChange={(e) =>
                  handleInput(car, e.target.name, e.target.value)
                } //onChange={(e)=>setCars({...car,make:e.target.value})}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Model</Form.Label>
              <Form.Control
                name="model"
                type="text"
                value={car.model}
                placeholder="Enter model"
                onChange={(e) =>
                  handleInput(car, e.target.name, e.target.value)
                }
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Year</Form.Label>
              <Form.Control
                name="year"
                type="number"
                value={car.year}
                placeholder="Enter year"
                onChange={(e) =>
                  handleInput(car, e.target.name, e.target.value)
                }
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Price</Form.Label>
              <Form.Control
                name="price"
                type="number"
                value={car.price}
                placeholder="Enter price"
                onChange={(e) =>
                  handleInput(car, e.target.name, e.target.value)
                }
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Description</Form.Label>
              <Form.Control
                name="description"
                type="text"
                value={car.description}
                placeholder="Enter description"
                onChange={(e) =>
                  handleInput(car, e.target.name, e.target.value)
                }
              />
            </Form.Group>
            <Button variant="primary" type="submit">
              Submit
            </Button>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default EditModal;
