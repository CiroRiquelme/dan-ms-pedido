INSERT INTO MS_PEDIDOS.ESTADO_PEDIDO (ID_EST_PED, DESC_ESTADO) VALUES 
    (1, 'NUEVO'),
    (2, 'CONFIRMADO'),
    (3, 'PENDIENTE'),
    (4, 'CANCELADO'),   
    (5, 'ACEPTADO'),
    (6, 'RECHAZADO'),   
    (7, 'EN PREPARACION'),
    (8, 'ENTREGADO');
    
INSERT INTO MS_PEDIDOS.OBRA (ID_OBRA, DESC_OBRA) VALUES
    (1, 'Obra 1'),
    (2, 'Obra 2'),
    (3, 'Obra 3');

INSERT INTO MS_PEDIDOS.PRODUCTO (ID_PROD, DESC_PROD, PREC_PROD) VALUES
    (1, 'Ladrillo', 10.00),
    (2, 'Cemento', 20.00),
    (3, 'Yeso', 30.00);

INSERT INTO MS_PEDIDOS.PEDIDO (FEC_PED, ID_EST_PED, ID_OBRA) VALUES
    ('2020-01-01 10:10:10', 1, 1),
    ('2020-01-01 10:10:10', 1, 2),
    ('2020-01-01 10:10:10', 1, 3);

INSERT INTO MS_PEDIDOS.DETALLE_PEDIDO (CAN_DET_PED, PREC_DET_PED, ID_PROD, ID_PED) VALUES 
    (10, 10.00, 1, 1),
    (20, 20.00, 2, 1),
    (10, 10.00, 1, 2),
    (20, 20.00, 2, 2),
    (10, 10.00, 1, 3),
    (20, 30.00, 3, 3);