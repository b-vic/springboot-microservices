import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { useCookies } from 'react-cookie';

const CustomerList = () => {

    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [cookies] = useCookies(['XSRF-TOKEN']);

    useEffect(() => {
        setLoading(true);

        fetch('customer')
            .then(response => response.json())
            .then(data => {
                setCustomers(data);
                setLoading(false);
            })
    }, []);

    const remove = async (id) => {
        await fetch(`/customer/${id}`, {
            method: 'DELETE',
            headers: {
                'X-XSRF-TOKEN': cookies['XSRF-TOKEN'],
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(() => {
            let updatedCustomers = [...customers].filter(i => i.customerId !== id);
            setCustomers(updatedCustomers);
        });
    }

    if (loading) {
        return <p>Loading...</p>;
    }

    const customerList = customers.map(customer => {
        return <tr key={customer.customerId}>
            <td style={{whiteSpace: 'nowrap'}}>{customer.customerId}</td>
            <td style={{whiteSpace: 'nowrap'}}>{customer.firstName} {customer.lastName}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/customers/" + customer.customerId}>Edit</Button>
                    &nbsp;
                    <Button size="sm" color="danger" onClick={() => remove(customer.customerId)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/customers/new">Add Customer</Button>
                </div>
                <h3>Customers</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="10%">ID</th>
                        <th width="30%">Name</th>
                        <th width="30%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {customerList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
};

export default CustomerList;