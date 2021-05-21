INSERT INTO MS_PEDIDOS.ESTADO_PEDIDO (DESC_ESTADO) VALUES 
    ('Aceptado'),
    ('Pendiente'),
    ('Enviado');

INSERT INTO MS_PEDIDOS.OBRA (ID_OBRA, DESC_OBRA) VALUES
    (1, 'obra1'),
    (2, 'obra2'),
    (3, 'obra3');

INSERT INTO MS_PEDIDOS.PRODUCTO (ID_PROD, DESC_PROD, PREC_PROD) VALUES
    (1, 'prod1', 10.00),
    (2, 'prod2', 20.00),
    (3, 'prod3', 30.00);

INSERT INTO MS_PEDIDOS.PEDIDO (FEC_PED, ID_EST_PED, ID_OBRA) VALUES
    ('2020-01-01 10:10:10', 1, 1),
    ('2020-01-01 10:10:10', 1, 2),
    ('2020-01-01 10:10:10', 3, 3);

INSERT INTO MS_PEDIDOS.DETALLE_PEDIDO (CAN_DET_PED, PREC_DET_PED, ID_PROD, ID_PED) VALUES 
    (1, 10.00, 1, 1),
    (2, 20.00, 2, 1),
    (3, 10.00, 1, 2),
    (4, 20.00, 2, 2),
    (5, 30.00, 3, 3),
    (6, 10.00, 1, 3);