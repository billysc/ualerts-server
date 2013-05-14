INSERT INTO building(id, abbreviation, name)
VALUES(building.1.id, 'building.1.abbreviation', 'building.1.name');

INSERT INTO room(id, building_id, room_number)
VALUES(room.1.id, building.1.id, 'room.1.roomNumber');

INSERT INTO room(id, building_id, room_number)
VALUES(room.2.id, building.1.id, 'room.2.roomNumber');

INSERT INTO position_hint(id, hint_text)
VALUES(positionHint.1.id, 'positionHint.1.hintText');

INSERT INTO position_hint(id, hint_text)
VALUES(positionHint.2.id, 'positionHint.2.hintText');

INSERT INTO asset(id, serial_number, inventory_number, mac_address)
VALUES(asset.1.id, 'asset.1.serialNumber', 'asset.1.inventoryNumber', 
  'asset.1.macAddress');
  
INSERT INTO asset(id, serial_number, inventory_number, mac_address)
VALUES(asset.2.id, 'asset.2.serialNumber', 'asset.2.inventoryNumber', 
  'asset.2.macAddress');

INSERT INTO fixture(id, room_id, position_hint_id, asset_id, ip_address, 
    installed_by)
VALUES(fixture.1.id, fixture.1.roomId, fixture.1.positionHintId, 
    fixture.1.assetId, 'fixture.1.ipAddress', 'fixture.1.installedBy');

INSERT INTO fixture(id, room_id, position_hint_id, asset_id, ip_address, 
    installed_by)
VALUES(fixture.2.id, fixture.2.roomId, fixture.2.positionHintId, 
    fixture.2.assetId, 'fixture.2.ipAddress', 'fixture.2.installedBy');

