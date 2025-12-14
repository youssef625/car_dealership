import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import NAVBAR from "../../componantes/NAVBAR";
import { Link } from "react-router-dom";
import { useState } from "react";
import LoginNav from "../../componantes/LoginNav";

const LoginAdminEmp = () => {
  const [form, setForm] = useState({
    email: "",
    password: "",
  });
  function handleSubmit(e) {
    e.preventDefault();
    if (!form.email || !form.password) {
      alert("Please fill all fields");
      return;
    }
    // send form to backend
    console.log(form);
  }
  return (
    <>
      <LoginNav />
      <div className="d-flex flex-column align-items-center justify-content-center">
        <h1>Login to your account</h1>
        <Form
          className="bg-white p-3 d-flex flex-column gap-3"
          onSubmit={handleSubmit}
        >
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Control
              type="email"
              placeholder="Enter email"
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
            />
            <Form.Text className="text-muted">
              We'll never share your email with anyone else.
            </Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Control
              type="password"
              placeholder="Password"
              value={form.password}
              onChange={(e) => setForm({ ...form, password: e.target.value })}
            />
          </Form.Group>

          <Button variant="primary" type="submit">
            Submit
          </Button>
        </Form>
      </div>
    </>
  );
};

export default LoginAdminEmp;
