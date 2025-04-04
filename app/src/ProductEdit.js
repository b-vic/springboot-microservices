import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { useCookies } from 'react-cookie';

const ProductEdit = () => {
    const initialFormState = {
        productId: '',
        sku: '',
        name: '',
        description: ''
    };
    const [product, setProduct] = useState(initialFormState);
    const navigate = useNavigate();
    const { id } = useParams();
    const [cookies] = useCookies(['XSRF-TOKEN']);

    useEffect(() => {
        if (id !== 'new') {
            fetch(`/product/${id}`)
                .then(response => response.json())
                .then(data => setProduct(data));
        }
    }, [id, setProduct]);

    const handleChange = (event) => {
        const { name, value } = event.target

        setProduct({ ...product, [name]: value })
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        await fetch("/product", {
            method: (id !== 'new') ? 'PUT' : 'POST',
            headers: {
                'X-XSRF-TOKEN': cookies['XSRF-TOKEN'],
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product),
            credentials: 'include'
        });
        setProduct(initialFormState);
        navigate('/products');
    }

    const title = <h2>{id !== 'new' ? 'Edit Product' : 'Add Product'}</h2>;

    return (<div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="sku">Product SKU</Label>
                        <Input type="text" name="sku" id="sku" value={product.sku || ''}
                               onChange={handleChange} autoComplete="sku" disabled={id !== 'new'} />
                    </FormGroup>
                    <FormGroup>
                        <Label for="name">Product Name</Label>
                        <Input type="text" name="name" id="name" value={product.name || ''}
                               onChange={handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Product Description</Label>
                        <Input type="text" name="description" id="description" value={product.description || ''}
                               onChange={handleChange} autoComplete="description"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/products">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    )
};

export default ProductEdit;