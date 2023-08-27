ALTER TABLE medicos
ADD ativo BOOLEAN;
UPDATE medicos
SET ativo = true;
