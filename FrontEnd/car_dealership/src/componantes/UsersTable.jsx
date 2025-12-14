import Button from "react-bootstrap/Button";

const UsersTable = ({ users, fullUsers, setUsers }) => {
  async function handleBanned(userp, banstate) {
    const token = localStorage.getItem("token");
    if (!token) {
      alert("You must login first.");
      return;
    }

    try {
      const action = !banstate ? "ban" : "unban";
      const res = await fetch(
        `${import.meta.env.VITE_FINAL_BASE_URL}/api/users/${action}/${
          userp.id
        }`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) throw new Error(`Failed to ${action} user`);

      const data = await res.json();
      console.log(`${action} successful:`, data);

      // تحديث الـ state بعد نجاح الطلب
      const newArr = fullUsers.map((user) => {
        if (user.id === userp.id) {
          return { ...user, banned: !banstate };
        }
        return user;
      });
      setUsers(newArr);
    } catch (error) {
      console.error("Ban/Unban error:", error);
      alert("Error updating user status");
    }
  }

  return (
    <div className="d-flex justify-content-center h-auto align-items-baseline">
      <table className="table table-striped-columns w-75">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {users.map((user, index) => (
            <tr key={index}>
              <th>{user.name}</th>
              <td>{user.email}</td>
              <td className="d-flex gap-2">
                <Button
                  onClick={() => handleBanned(user, user.banned)}
                  variant="warning"
                  disabled={user.banned}
                >
                  ban
                </Button>

                <Button
                  onClick={() => handleBanned(user, user.banned)}
                  variant="secondary"
                  disabled={!user.banned}
                >
                  unban
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UsersTable;
