package utn.isi.dan.pedido.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LogAspect {
	
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    
    @Pointcut("execution(* utn.isi.dan.pedido.service.implement.PedidoServiceImpl*.*(..))")
    private void metodosServicios() {}

    @Pointcut("execution(* utn.isi.dan.pedido.dao.*.*(..))")
    private void metodosRepositorios() {}

    @Before("metodosServicios() || metodosRepositorios()" )
    public void hacerAntes(JoinPoint joinPoint) {
        logger.info(joinPoint.getTarget().getClass() + 
            "	<Before> Metodo: " + joinPoint.getSignature().getName() +"	CantArgs: " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("metodosServicios() || metodosRepositorios()")
    public void hacerDespues(JoinPoint joinPoint) {
        logger.info(joinPoint.getTarget().getClass() + 
            "	<After> Metodo: " + joinPoint.getSignature().getName() +"	CantArgs: " + Arrays.toString(joinPoint.getArgs()));
    }


}
