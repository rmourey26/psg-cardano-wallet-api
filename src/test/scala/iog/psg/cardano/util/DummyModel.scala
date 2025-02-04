package iog.psg.cardano.util

import java.time.ZonedDateTime
import java.util.UUID

import io.circe.parser.parse
import iog.psg.cardano.CardanoApiCodec._
import iog.psg.cardano.TxMetadataOut
import org.scalatest.Assertions

trait DummyModel { self: Assertions =>

  final val randomWalletName = UUID.randomUUID().toString
  final val oldPassword = "old_password"
  final val newPassword = "new_password"
  final val walletPassphrase = UUID.randomUUID().toString
  protected val withdrawal = "500"
  final val addressPoolGap = 500
  final val stakePoolId = "pool1wqaz0q0zhtxlgn0ewssevn2mrtm30fgh2g7hr7z9rj5856457mm"

  final lazy val dummyDateTime = ZonedDateTime.parse("2000-01-02T03:04:05.000Z")

  final val addressIdStr =
    "addr1sjck9mdmfyhzvjhydcjllgj9vjvl522w0573ncustrrr2rg7h9azg4cyqd36yyd48t5ut72hgld0fg2xfvz82xgwh7wal6g2xt8n996s3xvu5g"

  final val inAddress = InAddress(
    address = Some(addressIdStr),
    amount = Some(QuantityUnit(quantity = 42000000, unit = Units.lovelace)),
    id = "1423856bc91c49e928f6f30f4e8d665d53eb4ab6028bd0ac971809d514c92db1",
    index = 0
  )

  final lazy val outAddress =
    OutAddress(address = addressIdStr, amount = QuantityUnit(quantity = 42000000, unit = Units.lovelace))

  final lazy val timedBlock = TimedBlock(
    time = dummyDateTime,
    block = Block(
      slotNumber = 1337,
      epochNumber = 14,
      height = QuantityUnit(1337, Units.block),
      absoluteSlotNumber = Some(8086)
    )
  )

  final lazy val metadataMap = Map(0L -> MetadataValueStr("0" * 64), 1L -> MetadataValueStr("1" * 64))

  final lazy val txMetadata = TxMetadataMapIn(metadataMap)

  final lazy val txMetadataOut =
    TxMetadataOut(json = parse("""
                                 |{
                                 |      "0": {
                                 |        "string": "cardano"
                                 |      },
                                 |      "1": {
                                 |        "int": 14
                                 |      },
                                 |      "2": {
                                 |        "bytes": "2512a00e9653fe49a44a5886202e24d77eeb998f"
                                 |      },
                                 |      "3": {
                                 |        "list": [
                                 |          {
                                 |            "int": 14
                                 |          },
                                 |          {
                                 |            "bytes": "2512a00e9653fe49a44a5886202e24d77eeb998f"
                                 |          },
                                 |          {
                                 |            "string": "1337"
                                 |          }
                                 |        ]
                                 |      },
                                 |      "4": {
                                 |        "map": [
                                 |          {
                                 |            "k": {
                                 |              "string": "key"
                                 |            },
                                 |            "v": {
                                 |              "bytes": "2512a00e9653fe49a44a5886202e24d77eeb998f"
                                 |            }
                                 |          },
                                 |          {
                                 |            "k": {
                                 |              "int": 14
                                 |            },
                                 |            "v": {
                                 |              "int": 42
                                 |            }
                                 |          }
                                 |        ]
                                 |      }
                                 |    }
                                 |""".stripMargin).getOrElse(fail("Invalid metadata json")))

  final lazy val firstTransactionId = "1423856bc91c49e928f6f30f4e8d665d53eb4ab6028bd0ac971809d514c92db1"

  //Year: 2000
  final lazy val oldTransactionsIdsAsc =
    Seq(firstTransactionId, "3423856bc91c49e928f6f30f4e8d665d53eb4ab6028bd0ac971809d514c92db1")

  //Year: 2020
  final lazy val newTransactionsIds = Seq("2423856bc91c49e928f6f30f4e8d665d53eb4ab6028bd0ac971809d514c92db1")

  final lazy val transactionsIdsDesc = (oldTransactionsIdsAsc ++ newTransactionsIds).sortWith(_ > _)

  final lazy val address = WalletAddress(
    addressStyle = "Shelley",
    stakeReference = "none",
    networkTag = 123,
    spendingKeyHash = "stringstringstringstringstringstringstringstringstringst",
    stakeKeyHash = "stringstringstringstringstringstringstringstringstringst",
    scriptHash = Some("stringstringstringstringstringstringstringstringstringstringstri"),
    pointer = Some(Pointer(slotNum = 0, transactionIndex = 1, outputIndex = 2)),
    addressRoot = Some("string"),
    derivationPath = Some("string_path")
  )

  final val addressesIds = Seq(
    WalletAddressId(
      id =
        "addr1sjck9mdmfyhzvjhydcjllgj9vjvl522w0573ncustrrr2rg7h9azg4cyqd36yyd48t5ut72hgld0fg2xfvz82xgwh7wal6g2xt8n996s3xvu5g",
      state = Some(AddressFilter.unUsed)
    ),
    WalletAddressId(
      id =
        "addr2sjck9mdmfyhzvjhydcjllgj9vjvl522w0573ncustrrr2rg7h9azg4cyqd36yyd48t5ut72hgld0fg2xfvz82xgwh7wal6g2xt8n996s3xvu5g",
      state = Some(AddressFilter.used)
    ),
    WalletAddressId(
      id =
        "addr3sjck9mdmfyhzvjhydcjllgj9vjvl522w0573ncustrrr2rg7h9azg4cyqd36yyd48t5ut72hgld0fg2xfvz82xgwh7wal6g2xt8n996s3xvu5g",
      state = Some(AddressFilter.unUsed)
    )
  )

  final lazy val unUsedAddresses = addressesIds.filter(_.state.contains(AddressFilter.unUsed))
  final lazy val usedAddresses = addressesIds.filter(_.state.contains(AddressFilter.used))
  final lazy val addressToInspect = unUsedAddresses.head

  final lazy val networkTip = NetworkTip(
    epochNumber = 14,
    slotNumber = 1337,
    height = Some(QuantityUnit(1337, Units.block)),
    absoluteSlotNumber = Some(8086)
  )

  final lazy val wallet = Wallet(
    id = "2512a00e9653fe49a44a5886202e24d77eeb998f",
    addressPoolGap = 20,
    balance = Balance(
      available = QuantityUnit(42000000, Units.lovelace),
      reward = QuantityUnit(42000000, Units.lovelace),
      total = QuantityUnit(42000000, Units.lovelace)
    ),
    delegation = Some(
      Delegation(
        active = DelegationActive(
          status = DelegationStatus.delegating,
          target = Some("1423856bc91c49e928f6f30f4e8d665d53eb4ab6028bd0ac971809d514c92db1")
        ),
        next = List(
          DelegationNext(
            status = DelegationStatus.notDelegating,
            changesAt =
              Some(NextEpoch(epochStartTime = ZonedDateTime.parse("2020-01-22T10:06:39.037Z"), epochNumber = 14))
          )
        )
      )
    ),
    name = "Alan's Wallet",
    passphrase = Some(Passphrase(lastUpdatedAt = ZonedDateTime.parse("2019-02-27T14:46:45.000Z"))),
    state = SyncStatus(SyncState.ready, None),
    tip = networkTip
  )

  final lazy val nodeTip = NodeTip(
    epochNumber = 14,
    slotNumber = 1337,
    height = QuantityUnit(1337, Units.block),
    absoluteSlotNumber = Some(8086)
  )

  final lazy val networkInfo = NetworkInfo(
    syncProgress = SyncStatus(SyncState.ready, None),
    networkTip = Some(networkTip.copy(height = None)),
    nodeTip = nodeTip,
    nextEpoch = Some(NextEpoch(dummyDateTime, 14))
  )

  final lazy val networkClock = NetworkClock(
    status = "available",
    offset = QuantityUnit(14, Units.microsecond)
  )

  final lazy val networkClockForced = NetworkClock(
    status = "available",
    offset = QuantityUnit(99, Units.microsecond)
  )
  
  final lazy val networkParameters = NetworkParameters(
    genesisBlockHash = "3c07030e36bfffe67e2e2ec09e5293d384637cd2f004356ef320f3fe6c52041a",
    blockchain_start_time = ZonedDateTime.parse("2019-02-27T14:46:45.000Z"),
    slotLength = QuantityUnit(10, Units.second),
    epochLength = QuantityUnit(42000, Units.slot),
    epochStability = Some(QuantityUnit(1337, Units.block)),
    activeSlotCoefficient = QuantityUnit(42, Units.percent),
    decentralizationLevel = QuantityUnit(42, Units.percent),
    desiredPoolNumber = 100,
    minimumUtxoValue = QuantityUnit(42000000, Units.lovelace),
    hardforkAt = Some(NextEpoch(
      epochStartTime = ZonedDateTime.parse("2019-02-27T14:46:45.000Z"),
      epochNumber = 14
    ))
  )

  final lazy val accountPublicKey = (1 to 128).map(_ => "a").mkString

  final lazy val mnemonicSentence = GenericMnemonicSentence("a b c d e a b c d e a b c d e")
  final lazy val mnemonicSecondFactor = GenericMnemonicSecondaryFactor("a b c d e a b c d")

  final lazy val payments = Payments(Seq(Payment(unUsedAddresses.head.id, QuantityUnit(100000, Units.lovelace))))

  final lazy val estimateFeeResponse = {
    val estimatedMin = QuantityUnit(quantity = 42000000, unit = Units.lovelace)
    EstimateFeeResponse(
      estimatedMin = estimatedMin,
      estimatedMax = estimatedMin.copy(quantity = estimatedMin.quantity * 3)
    )
  }

  final lazy val fundPaymentsResponse =
    FundPaymentsResponse(inputs = IndexedSeq(inAddress), outputs = Seq(outAddress))

  final lazy val uTxOStatistics = UTxOStatistics(
    total = QuantityUnit(quantity = 42000000, unit = Units.lovelace),
    scale = "log10",
    distribution = Map(
      "10" -> 1,
      "100" -> 0,
      "1000" -> 8,
      "10000" -> 14,
      "100000" -> 32,
      "1000000" -> 3,
      "10000000" -> 0,
      "100000000" -> 12,
      "1000000000" -> 0,
      "10000000000" -> 0,
      "100000000000" -> 0,
      "1000000000000" -> 0,
      "10000000000000" -> 0,
      "100000000000000" -> 0,
      "1000000000000000" -> 0,
      "10000000000000000" -> 0,
      "45000000000000000" -> 0
    )
  )

}
