import React from 'react';
import { Link } from 'react-router-dom';
import { Card } from 'react-bootstrap';

const BookItem = (props) => {
  const { id, title } = props.book;

  return (
    <Card>
      <Card.Body>
        <Card.Title>{title}</Card.Title>
        <Link to={'/post/' + id} className="btn btn-primary">
          상세보기
        </Link>
      </Card.Body>
    </Card>
  );
};

export default BookItem;
