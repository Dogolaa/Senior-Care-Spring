CREATE TABLE medications (
    id UUID PRIMARY KEY,
    commercial_name VARCHAR(255) NOT NULL,
    active_ingredient VARCHAR(255) NOT NULL,
    pharmaceutical_form VARCHAR(100) NOT NULL,
    concentration VARCHAR(100),
    manufacturer VARCHAR(255),
    registration_number VARCHAR(100),
    therapeutic_class VARCHAR(100) NOT NULL,
    controlled_substance BOOLEAN NOT NULL DEFAULT FALSE
);

-- Anti-hipertensivos
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('a1000001-0000-0000-0000-000000000001', 'Losartana Potássica 50mg', 'Losartana Potássica', 'Comprimido revestido', '50mg', 'Genérico', 'ANVISA-001', 'Anti-hipertensivo', FALSE),
('a1000001-0000-0000-0000-000000000002', 'Losartana Potássica 100mg', 'Losartana Potássica', 'Comprimido revestido', '100mg', 'Genérico', 'ANVISA-002', 'Anti-hipertensivo', FALSE),
('a1000001-0000-0000-0000-000000000003', 'Enalapril 5mg', 'Enalapril Maleato', 'Comprimido', '5mg', 'Genérico', 'ANVISA-003', 'Anti-hipertensivo', FALSE),
('a1000001-0000-0000-0000-000000000004', 'Enalapril 10mg', 'Enalapril Maleato', 'Comprimido', '10mg', 'Genérico', 'ANVISA-004', 'Anti-hipertensivo', FALSE),
('a1000001-0000-0000-0000-000000000005', 'Amlodipina 5mg', 'Amlodipina Besilato', 'Comprimido', '5mg', 'Genérico', 'ANVISA-005', 'Anti-hipertensivo', FALSE),
('a1000001-0000-0000-0000-000000000006', 'Amlodipina 10mg', 'Amlodipina Besilato', 'Comprimido', '10mg', 'Genérico', 'ANVISA-006', 'Anti-hipertensivo', FALSE),
('a1000001-0000-0000-0000-000000000007', 'Hidroclorotiazida 25mg', 'Hidroclorotiazida', 'Comprimido', '25mg', 'Genérico', 'ANVISA-007', 'Diurético / Anti-hipertensivo', FALSE),
('a1000001-0000-0000-0000-000000000008', 'Metoprolol 50mg', 'Metoprolol Succinato', 'Comprimido de liberação prolongada', '50mg', 'Genérico', 'ANVISA-008', 'Beta-bloqueador', FALSE),
('a1000001-0000-0000-0000-000000000009', 'Propranolol 40mg', 'Propranolol Cloridrato', 'Comprimido', '40mg', 'Genérico', 'ANVISA-009', 'Beta-bloqueador', FALSE),
('a1000001-0000-0000-0000-000000000010', 'Carvedilol 6,25mg', 'Carvedilol', 'Comprimido', '6,25mg', 'Genérico', 'ANVISA-010', 'Beta-bloqueador', FALSE);

-- Antiagregantes e Anticoagulantes
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('a2000002-0000-0000-0000-000000000001', 'AAS 100mg', 'Ácido Acetilsalicílico', 'Comprimido revestido', '100mg', 'Genérico', 'ANVISA-011', 'Antiagregante plaquetário', FALSE),
('a2000002-0000-0000-0000-000000000002', 'Clopidogrel 75mg', 'Clopidogrel Bissulfato', 'Comprimido revestido', '75mg', 'Genérico', 'ANVISA-012', 'Antiagregante plaquetário', FALSE),
('a2000002-0000-0000-0000-000000000003', 'Varfarina Sódica 5mg', 'Varfarina Sódica', 'Comprimido', '5mg', 'Genérico', 'ANVISA-013', 'Anticoagulante', FALSE),
('a2000002-0000-0000-0000-000000000004', 'Rivaroxabana 20mg', 'Rivaroxabana', 'Comprimido revestido', '20mg', 'Bayer', 'ANVISA-014', 'Anticoagulante', FALSE);

-- Estatinas / Hipolipemiantes
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('a3000003-0000-0000-0000-000000000001', 'Atorvastatina 20mg', 'Atorvastatina Cálcica', 'Comprimido revestido', '20mg', 'Genérico', 'ANVISA-015', 'Hipolipemiante', FALSE),
('a3000003-0000-0000-0000-000000000002', 'Atorvastatina 40mg', 'Atorvastatina Cálcica', 'Comprimido revestido', '40mg', 'Genérico', 'ANVISA-016', 'Hipolipemiante', FALSE),
('a3000003-0000-0000-0000-000000000003', 'Sinvastatina 20mg', 'Sinvastatina', 'Comprimido revestido', '20mg', 'Genérico', 'ANVISA-017', 'Hipolipemiante', FALSE),
('a3000003-0000-0000-0000-000000000004', 'Sinvastatina 40mg', 'Sinvastatina', 'Comprimido revestido', '40mg', 'Genérico', 'ANVISA-018', 'Hipolipemiante', FALSE),
('a3000003-0000-0000-0000-000000000005', 'Rosuvastatina 10mg', 'Rosuvastatina Cálcica', 'Comprimido revestido', '10mg', 'Genérico', 'ANVISA-019', 'Hipolipemiante', FALSE);

-- Antidiabéticos
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('a4000004-0000-0000-0000-000000000001', 'Metformina 500mg', 'Cloridrato de Metformina', 'Comprimido revestido', '500mg', 'Genérico', 'ANVISA-020', 'Antidiabético', FALSE),
('a4000004-0000-0000-0000-000000000002', 'Metformina 850mg', 'Cloridrato de Metformina', 'Comprimido revestido', '850mg', 'Genérico', 'ANVISA-021', 'Antidiabético', FALSE),
('a4000004-0000-0000-0000-000000000003', 'Glibenclamida 5mg', 'Glibenclamida', 'Comprimido', '5mg', 'Genérico', 'ANVISA-022', 'Antidiabético', FALSE),
('a4000004-0000-0000-0000-000000000004', 'Insulina NPH 100UI/mL', 'Insulina NPH Humana', 'Solução injetável', '100UI/mL', 'Novo Nordisk', 'ANVISA-023', 'Antidiabético', FALSE),
('a4000004-0000-0000-0000-000000000005', 'Insulina Regular 100UI/mL', 'Insulina Regular Humana', 'Solução injetável', '100UI/mL', 'Novo Nordisk', 'ANVISA-024', 'Antidiabético', FALSE);

-- Analgésicos e Anti-inflamatórios
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('a5000005-0000-0000-0000-000000000001', 'Dipirona Sódica 500mg', 'Dipirona Sódica', 'Comprimido', '500mg', 'Genérico', 'ANVISA-025', 'Analgésico / Antitérmico', FALSE),
('a5000005-0000-0000-0000-000000000002', 'Dipirona Sódica Gotas', 'Dipirona Sódica', 'Solução oral', '500mg/mL', 'Genérico', 'ANVISA-026', 'Analgésico / Antitérmico', FALSE),
('a5000005-0000-0000-0000-000000000003', 'Paracetamol 500mg', 'Paracetamol', 'Comprimido', '500mg', 'Genérico', 'ANVISA-027', 'Analgésico / Antitérmico', FALSE),
('a5000005-0000-0000-0000-000000000004', 'Paracetamol Gotas', 'Paracetamol', 'Solução oral', '200mg/mL', 'Genérico', 'ANVISA-028', 'Analgésico / Antitérmico', FALSE),
('a5000005-0000-0000-0000-000000000005', 'Ibuprofeno 600mg', 'Ibuprofeno', 'Comprimido revestido', '600mg', 'Genérico', 'ANVISA-029', 'Anti-inflamatório', FALSE),
('a5000005-0000-0000-0000-000000000006', 'Tramadol 50mg', 'Cloridrato de Tramadol', 'Cápsula', '50mg', 'Genérico', 'ANVISA-030', 'Analgésico Opioide', TRUE),
('a5000005-0000-0000-0000-000000000007', 'Tramadol Injetável 100mg/2mL', 'Cloridrato de Tramadol', 'Solução injetável', '100mg/2mL', 'Genérico', 'ANVISA-031', 'Analgésico Opioide', TRUE);

-- Gastrointestinais
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('a6000006-0000-0000-0000-000000000001', 'Omeprazol 20mg', 'Omeprazol', 'Cápsula', '20mg', 'Genérico', 'ANVISA-032', 'Inibidor de bomba de prótons', FALSE),
('a6000006-0000-0000-0000-000000000002', 'Omeprazol 40mg', 'Omeprazol', 'Cápsula', '40mg', 'Genérico', 'ANVISA-033', 'Inibidor de bomba de prótons', FALSE),
('a6000006-0000-0000-0000-000000000003', 'Pantoprazol 40mg', 'Pantoprazol Sódico', 'Comprimido gastrorresistente', '40mg', 'Genérico', 'ANVISA-034', 'Inibidor de bomba de prótons', FALSE),
('a6000006-0000-0000-0000-000000000004', 'Metoclopramida 10mg', 'Cloridrato de Metoclopramida', 'Comprimido', '10mg', 'Genérico', 'ANVISA-035', 'Procinético / Antiemético', FALSE),
('a6000006-0000-0000-0000-000000000005', 'Metoclopramida Gotas', 'Cloridrato de Metoclopramida', 'Solução oral', '4mg/mL', 'Genérico', 'ANVISA-036', 'Procinético / Antiemético', FALSE),
('a6000006-0000-0000-0000-000000000006', 'Lactulose Xarope', 'Lactulose', 'Xarope', '667mg/mL', 'Genérico', 'ANVISA-037', 'Laxante osmótico', FALSE),
('a6000006-0000-0000-0000-000000000007', 'Bromoprida 10mg', 'Bromoprida', 'Comprimido', '10mg', 'Genérico', 'ANVISA-038', 'Procinético / Antiemético', FALSE);

-- Psicotrópicos e Neurológicos
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('a7000007-0000-0000-0000-000000000001', 'Alprazolam 0,25mg', 'Alprazolam', 'Comprimido', '0,25mg', 'Genérico', 'ANVISA-039', 'Benzodiazepínico', TRUE),
('a7000007-0000-0000-0000-000000000002', 'Alprazolam 0,5mg', 'Alprazolam', 'Comprimido', '0,5mg', 'Genérico', 'ANVISA-040', 'Benzodiazepínico', TRUE),
('a7000007-0000-0000-0000-000000000003', 'Clonazepam 0,5mg', 'Clonazepam', 'Comprimido', '0,5mg', 'Genérico', 'ANVISA-041', 'Benzodiazepínico', TRUE),
('a7000007-0000-0000-0000-000000000004', 'Clonazepam 2mg', 'Clonazepam', 'Comprimido', '2mg', 'Genérico', 'ANVISA-042', 'Benzodiazepínico', TRUE),
('a7000007-0000-0000-0000-000000000005', 'Diazepam 5mg', 'Diazepam', 'Comprimido', '5mg', 'Genérico', 'ANVISA-043', 'Benzodiazepínico', TRUE),
('a7000007-0000-0000-0000-000000000006', 'Risperidona 2mg', 'Risperidona', 'Comprimido revestido', '2mg', 'Genérico', 'ANVISA-044', 'Antipsicótico', FALSE),
('a7000007-0000-0000-0000-000000000007', 'Haloperidol 1mg', 'Haloperidol', 'Comprimido', '1mg', 'Genérico', 'ANVISA-045', 'Antipsicótico', FALSE),
('a7000007-0000-0000-0000-000000000008', 'Quetiapina 25mg', 'Fumarato de Quetiapina', 'Comprimido revestido', '25mg', 'Genérico', 'ANVISA-046', 'Antipsicótico', FALSE),
('a7000007-0000-0000-0000-000000000009', 'Donepezila 5mg', 'Cloridrato de Donepezila', 'Comprimido revestido', '5mg', 'Genérico', 'ANVISA-047', 'Anticolinesterásico (Alzheimer)', FALSE),
('a7000007-0000-0000-0000-000000000010', 'Donepezila 10mg', 'Cloridrato de Donepezila', 'Comprimido revestido', '10mg', 'Genérico', 'ANVISA-048', 'Anticolinesterásico (Alzheimer)', FALSE),
('a7000007-0000-0000-0000-000000000011', 'Memantina 10mg', 'Cloridrato de Memantina', 'Comprimido revestido', '10mg', 'Genérico', 'ANVISA-049', 'Antagonista NMDA (Alzheimer)', FALSE),
('a7000007-0000-0000-0000-000000000012', 'Galantamina 8mg', 'Bromidrato de Galantamina', 'Cápsula de liberação prolongada', '8mg', 'Janssen', 'ANVISA-050', 'Anticolinesterásico (Alzheimer)', FALSE),
('a7000007-0000-0000-0000-000000000013', 'Sertralina 50mg', 'Cloridrato de Sertralina', 'Comprimido revestido', '50mg', 'Genérico', 'ANVISA-051', 'Antidepressivo ISRS', FALSE),
('a7000007-0000-0000-0000-000000000014', 'Escitalopram 10mg', 'Oxalato de Escitalopram', 'Comprimido revestido', '10mg', 'Genérico', 'ANVISA-052', 'Antidepressivo ISRS', FALSE),
('a7000007-0000-0000-0000-000000000015', 'Mirtazapina 30mg', 'Mirtazapina', 'Comprimido revestido', '30mg', 'Genérico', 'ANVISA-053', 'Antidepressivo', FALSE);

-- Vitaminas e Suplementos
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('a8000008-0000-0000-0000-000000000001', 'Vitamina D3 1000UI', 'Colecalciferol', 'Cápsula', '1000UI', 'Genérico', 'ANVISA-054', 'Vitamina / Suplemento', FALSE),
('a8000008-0000-0000-0000-000000000002', 'Vitamina D3 2000UI', 'Colecalciferol', 'Cápsula', '2000UI', 'Genérico', 'ANVISA-055', 'Vitamina / Suplemento', FALSE),
('a8000008-0000-0000-0000-000000000003', 'Carbonato de Cálcio 500mg', 'Carbonato de Cálcio', 'Comprimido mastigável', '500mg', 'Genérico', 'ANVISA-056', 'Suplemento mineral', FALSE),
('a8000008-0000-0000-0000-000000000004', 'Ácido Fólico 5mg', 'Ácido Fólico', 'Comprimido', '5mg', 'Genérico', 'ANVISA-057', 'Vitamina / Suplemento', FALSE),
('a8000008-0000-0000-0000-000000000005', 'Vitamina B12 1000mcg', 'Cianocobalamina', 'Comprimido', '1000mcg', 'Genérico', 'ANVISA-058', 'Vitamina / Suplemento', FALSE),
('a8000008-0000-0000-0000-000000000006', 'Complexo B', 'Complexo Vitamínico B', 'Comprimido revestido', 'Dose padrão', 'Genérico', 'ANVISA-059', 'Vitamina / Suplemento', FALSE);

-- Antibióticos
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('a9000009-0000-0000-0000-000000000001', 'Amoxicilina 500mg', 'Amoxicilina Triidratada', 'Cápsula', '500mg', 'Genérico', 'ANVISA-060', 'Antibiótico', FALSE),
('a9000009-0000-0000-0000-000000000002', 'Cefalexina 500mg', 'Cefalexina Monoidratada', 'Cápsula', '500mg', 'Genérico', 'ANVISA-061', 'Antibiótico', FALSE),
('a9000009-0000-0000-0000-000000000003', 'Azitromicina 500mg', 'Di-Hidrato de Azitromicina', 'Comprimido revestido', '500mg', 'Genérico', 'ANVISA-062', 'Antibiótico', FALSE),
('a9000009-0000-0000-0000-000000000004', 'Ciprofloxacino 500mg', 'Cloridrato de Ciprofloxacino', 'Comprimido revestido', '500mg', 'Genérico', 'ANVISA-063', 'Antibiótico', FALSE);

-- Tireóideos
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('ab000010-0000-0000-0000-000000000001', 'Levotiroxina 25mcg', 'Levotiroxina Sódica', 'Comprimido', '25mcg', 'Genérico', 'ANVISA-064', 'Hormônio tireoidiano', FALSE),
('ab000010-0000-0000-0000-000000000002', 'Levotiroxina 50mcg', 'Levotiroxina Sódica', 'Comprimido', '50mcg', 'Genérico', 'ANVISA-065', 'Hormônio tireoidiano', FALSE),
('ab000010-0000-0000-0000-000000000003', 'Levotiroxina 100mcg', 'Levotiroxina Sódica', 'Comprimido', '100mcg', 'Genérico', 'ANVISA-066', 'Hormônio tireoidiano', FALSE);

-- Diuréticos
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('ac000011-0000-0000-0000-000000000001', 'Furosemida 40mg', 'Furosemida', 'Comprimido', '40mg', 'Genérico', 'ANVISA-067', 'Diurético de alça', FALSE),
('ac000011-0000-0000-0000-000000000002', 'Espironolactona 25mg', 'Espironolactona', 'Comprimido revestido', '25mg', 'Genérico', 'ANVISA-068', 'Diurético poupador de potássio', FALSE),
('ac000011-0000-0000-0000-000000000003', 'Espironolactona 50mg', 'Espironolactona', 'Comprimido revestido', '50mg', 'Genérico', 'ANVISA-069', 'Diurético poupador de potássio', FALSE);

-- Broncodilatadores
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('ad000012-0000-0000-0000-000000000001', 'Salbutamol Aerossol', 'Sulfato de Salbutamol', 'Aerossol para inalação', '100mcg/dose', 'Genérico', 'ANVISA-070', 'Broncodilatador', FALSE),
('ad000012-0000-0000-0000-000000000002', 'Ipratrópio Solução', 'Brometo de Ipratrópio', 'Solução para inalação', '0,25mg/mL', 'Genérico', 'ANVISA-071', 'Broncodilatador', FALSE),
('ad000012-0000-0000-0000-000000000003', 'Budesonida Aerossol', 'Budesonida', 'Aerossol para inalação', '200mcg/dose', 'AstraZeneca', 'ANVISA-072', 'Corticoide inalatório', FALSE);

-- Outros
INSERT INTO medications (id, commercial_name, active_ingredient, pharmaceutical_form, concentration, manufacturer, registration_number, therapeutic_class, controlled_substance) VALUES
('ae000013-0000-0000-0000-000000000001', 'Alopurinol 300mg', 'Alopurinol', 'Comprimido', '300mg', 'Genérico', 'ANVISA-073', 'Antigotoso', FALSE),
('ae000013-0000-0000-0000-000000000002', 'Colchicina 0,5mg', 'Colchicina', 'Comprimido', '0,5mg', 'Genérico', 'ANVISA-074', 'Antigotoso', FALSE),
('ae000013-0000-0000-0000-000000000003', 'Prednisona 20mg', 'Prednisona', 'Comprimido', '20mg', 'Genérico', 'ANVISA-075', 'Corticoide sistêmico', FALSE),
('ae000013-0000-0000-0000-000000000004', 'Prednisona 5mg', 'Prednisona', 'Comprimido', '5mg', 'Genérico', 'ANVISA-076', 'Corticoide sistêmico', FALSE),
('ae000013-0000-0000-0000-000000000005', 'Digoxina 0,25mg', 'Digoxina', 'Comprimido', '0,25mg', 'Genérico', 'ANVISA-077', 'Cardiotônico', FALSE),
('ae000013-0000-0000-0000-000000000006', 'Amiodarona 200mg', 'Amiodarona Cloridrato', 'Comprimido', '200mg', 'Genérico', 'ANVISA-078', 'Antiarrítmico', FALSE);

-- Alterar as colunas medication_id nas tabelas existentes de VARCHAR para UUID com FK
ALTER TABLE prescriptions DROP COLUMN medication_id;
ALTER TABLE prescriptions ADD COLUMN medication_id UUID NOT NULL REFERENCES medications(id);

ALTER TABLE medication_records DROP COLUMN medication_id;
ALTER TABLE medication_records ADD COLUMN medication_id UUID NOT NULL REFERENCES medications(id);
